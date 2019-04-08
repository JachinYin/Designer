package com.jachin.des.service;

import com.jachin.des.def.DataDef;
import com.jachin.des.entity.*;
import com.jachin.des.mapper.CashFlowMapper;
import com.jachin.des.mapper.DesignerMapper;
import com.jachin.des.mapper.TemplateMapper;
import com.jachin.des.util.CommTool;
import com.jachin.des.util.CurrentUser;
import com.jachin.des.util.ResParam;
import com.jachin.des.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * CashFlow 表的服务层
 *
 * @author Jachin
 * @since 2019/3/21 22:13
 */
@Service
public class CashFlowService {

    @Autowired
    CashFlowMapper cashFlowMapper;

    @Autowired
    DesignerMapper designerMapper;

    @Autowired
    TemplateMapper templateMapper;

    // 应该无用
    public Response addCashFlow(CashFlow cashFlow, String typeName){
        int type = DataDef.getCashFlag(typeName);
        if(type == 0) return new Response(false, "操作失败，没有指定操作类型");
        if(cashFlow.getPrice() < 0) return new Response(false, "操作失败，金额错误。");

        /*
         * 收入
         * 1.新增收入记录
         * 2.修改设计师总收入，余额
         * 提现
         * 1.新增提现记录
         * 2.修改设计师余额
         */

        int aid = cashFlow.getAid(); // 拿aid是为了要对设计师表进行修改
        SearchArg searchArg = new SearchArg();
        searchArg.setAid(aid);
        Designer designer = designerMapper.getDesigner(searchArg);
        if(designer == null) return new Response(false); // 如果拿不到设计师信息，说明是数据库读取出错

        if(type == DataDef.CashFlag.INCOME){
            int balance = designer.getBalance() + cashFlow.getPrice();
            int totalPrice = designer.getTotalPrice() + cashFlow.getPrice();
            Designer data = new Designer();
            data.setAid(aid);
            data.setBalance(balance);
            data.setTotalPrice(totalPrice);
            int rt = designerMapper.setDesigner(data);
            if(rt == 0) return new Response(false);
            return new Response(true);
        }
        else if(type == DataDef.CashFlag.WITHDRAW){
            int balance = designer.getBalance() + cashFlow.getPrice();
            Designer data = new Designer();
            data.setAid(aid);
            data.setBalance(balance);
            int rt = designerMapper.setDesigner(data);
            if(rt == 0) return new Response(false);
            return new Response(true);
        }

        return new Response(false);
    }

    // 设计师后台获取提现记录
    public Response getCashFlowShowList(SearchArg searchArg){
        List<Designer> designerList = designerMapper.getDesignerList(new SearchArg());
        ResParam designerParam = new ResParam();
        for(Designer item: designerList){
            designerParam.put(String.valueOf(item.getAid()), item.getNickName());
        }
        searchArg.setType(DataDef.CashFlag.WITHDRAW);
        // 结果按时间降序排序
        searchArg.setCompColumns("time", true);
        // 这个拿到的会是单纯的现金流表的数据
        List<CashFlow> cashFlowList = cashFlowMapper.getCashFlowList(searchArg);
        // 这个是最终返回的数据集
        List<ResParam> list = new ArrayList<>();

        for(CashFlow item: cashFlowList){
            ResParam resParam = new ResParam();
            CommTool.mergeResParam(resParam, item);
            resParam.put("nickName", designerParam.get(item.getAid()+""));
            list.add(resParam);
         }
        Response response = new Response(true, "获取提现数据~");
        response.setData(new ResParam("list", list));
        return response;
    }

    // 设计师后台获取分佣管理详情信息
    @Transactional
    public Response getCashDetail(SearchArg searchArg){
        int aid = searchArg.getAid();
        if(aid < 1) return new Response(false, "账户ID错误。");

        // 获取设计师信息
        Designer designer = designerMapper.getDesigner(searchArg);
        if (designer == null) return new Response(false, "获取信息失败。");

        Response response = new Response(true, "获取信息成功");
        ResParam resParam = new ResParam();
        // 获取提现总数目
        searchArg.setType(DataDef.CashFlag.WITHDRAW);
        Integer sumWithdraw = cashFlowMapper.getSumWithdraw(searchArg);
        ResParam temp = new ResParam();
        temp.put("totalWithdraw", sumWithdraw == null ? 0:sumWithdraw);
        CommTool.mergeResParam(temp, designer);
        resParam.put("desData", temp);

        // 获取提现记录
//        searchArg.setType(DataDef.CashFlag.WITHDRAW);
        searchArg.setCompColumns("time", true);
        List<CashFlow> cashRecList = cashFlowMapper.getCashFlowList(searchArg);
        resParam.put("cashRecList", cashRecList);
        // 获取模板收入记录
        /*searchArg.setType(0); // 清空模板，使用自定义字符串
        searchArg.setExtra(String.format("AND type IN (%d, %d)", DataDef.CashFlag.INCOME, DataDef.CashFlag.DELTA_PRICE));
        List<CashFlow> cashTempList = cashFlowMapper.getCashFlowList(searchArg);
        List<Integer> tempIdList = new ArrayList<>();
        for (CashFlow cashFlow : cashTempList) {
            tempIdList.add(cashFlow.getTempId());
        }
        ResParam tempIdName = new ResParam();
        List<Template> templateList = templateMapper.getTemplateList(searchArg);
        for (Template template : templateList) {
            if(tempIdList.contains(template.getTempId())){
                tempIdName.put(String.valueOf(template.getTempId()), template.getTitle());
            }
        }*/
        List<CashFlowWithTitle> cashTempList = cashFlowMapper.getCashFlowWithTempTitle(searchArg);

        resParam.put("cashTempList", cashTempList);


        response.setData(resParam);
        return response;
    }

    //=====基础增删改查=====

    public Response getCashFlow(SearchArg searchArg){
        if(searchArg.getId() <= 0) return new Response(false, "请求参数错误。");
        Response response = new Response(true, "获取记录成功");
        CashFlow cashFlow = cashFlowMapper.getCashFlow(searchArg);
        response.setData(new ResParam("cashFlowData", cashFlow));
        return response;
    }

    // 获取现金表记录
    public Response getCashFlowList(SearchArg searchArg) {
        Response response = new Response(true, "获取收入/提现表记录");
        List<CashFlow> list = cashFlowMapper.getCashFlowList(searchArg);
        ResParam resParam = new ResParam();
        resParam.put("list", list);
        response.setData(resParam);
        return response;
    }
    
    public Response setCashFlow(CashFlow cashFlow){
        if(cashFlow.getId() < 1) return new Response(false, "请求参数错误。");
        int rt = cashFlowMapper.setCashFlow(cashFlow);
        if(rt ==0) return new Response(false);
        
        return new Response(true);
    }

    // 提现，插入现金表
    @Transactional
    public Response withdraw(CashFlow cashFlow){

        int aid = CurrentUser.getCurrentAid();
        int price = cashFlow.getPrice();
        if(aid < 1) return new Response(false, "账户ID错误。");
        if(price < 0) return new Response(false, "提现金额错误。");


        // 获取设计师余额
        SearchArg searchArg = new SearchArg();
        searchArg.setAid(aid);
        Designer designer = designerMapper.getDesigner(searchArg);
        if(designer == null) return new Response(false, "获取余额失败，请稍后重试。");
        int balance = designer.getBalance();
        if(balance - price < 0) return new Response(false, "余额不足");

        // 插入现金表
        cashFlow.setAid(aid);
        cashFlow.setType(DataDef.CashFlag.WITHDRAW);
        cashFlow.setBalance(balance - price);
        int rt = cashFlowMapper.addCashFlow(cashFlow);
        if(rt == 0) return new Response(false);

        // 修改设计师余额
        Designer setDesigner = new Designer();
        setDesigner.setAid(aid);
        setDesigner.setBalance(balance - price == 0 ? -1 : balance-price);
        rt = designerMapper.setDesigner(setDesigner);
        if (rt==0) return new Response(false);

        return new Response(true);
    }
    
    public Response delCashFlow(SearchArg searchArg){
        if(searchArg.getId() < 0) return new Response(false, "请求参数错误。");
        int rt = cashFlowMapper.delCashFlow(searchArg);
        if(rt == 0) return new Response(false);
        return new Response(true);
    }

    // 设计师前台，获取账户明细数据
    public Response getCashData() {
        int aid = CurrentUser.getCurrentAid();
        if(aid < 1) return new Response(false, "账户ID错误。");

        SearchArg searchArg = new SearchArg();
        searchArg.setAid(aid);
        // 获取设计师信息，拿余额和总收入
        Designer designer = designerMapper.getDesigner(searchArg);
        if(designer == null) return new Response(false, "获取设计师信息失败。");
        int balance = designer.getBalance();
        int totalPrice = designer.getTotalPrice();

        // 获取该设计师的现金记录
        searchArg.setCompColumns("time", true);
        List<CashFlow> cashFlowList = cashFlowMapper.getCashFlowList(searchArg);
        if(cashFlowList == null) return new Response(false, "获取现金记录失败。");

        // searchArg 设定了aid 和状态为 通过
        searchArg.setStatus(DataDef.TemplateStatus.PASS);
        // 那该设计师通过的模板数目
        List<Template> templateList = templateMapper.getTemplateList(searchArg);
        int passTemplate = 0;
        if(templateList != null) passTemplate = templateList.size();

        // 组合数据
        ResParam resParam = new ResParam();
        resParam.put("balance", balance);
        resParam.put("totalPrice", totalPrice);
        resParam.put("passTemplate", passTemplate);
        resParam.put("cashFlowList", cashFlowList);

        Response response = new Response(true, "获取账户明细数据");
        response.setData(resParam);
        return response;
    }
}
