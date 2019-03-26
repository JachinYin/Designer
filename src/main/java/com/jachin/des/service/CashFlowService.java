package com.jachin.des.service;

import com.jachin.des.entity.*;
import com.jachin.des.mapper.CashFlowMapper;
import com.jachin.des.mapper.DesignerMapper;
import com.jachin.des.util.CommTool;
import com.jachin.des.util.ResParam;
import com.jachin.des.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Response getCashFlowShowList(SearchArg searchArg){
        List<Designer> designerList = designerMapper.getDesignerList(new SearchArg());
        ResParam designerParam = new ResParam();
        for(Designer item: designerList){
            designerParam.put(String.valueOf(item.getAid()), item.getNickName());
        }
//        searchArg.setType(DataDef.CashFlag.WITHDRAW);
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


    //=====基础增删改查=====

    public Response getCashFlow(SearchArg searchArg){
        if(searchArg.getId() <= 0) return new Response(false, "请求参数错误。");
        Response response = new Response(true, "获取记录成功");
        CashFlow cashFlow = cashFlowMapper.getCashFlow(searchArg);
        response.setData(new ResParam("cashFlowData", cashFlow));
        return response;
    }

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

    public Response addCashFlow(CashFlow cashFlow){
        int rt = cashFlowMapper.addCashFlow(cashFlow);
        if(rt == 0) return new Response(false);
        return new Response(true);
    }
    
    public Response delCashFlow(SearchArg searchArg){
        if(searchArg.getId() < 0) return new Response(false, "请求参数错误。");
        int rt = cashFlowMapper.delCashFlow(searchArg);
        if(rt == 0) return new Response(false);
        return new Response(true);
    }
}
