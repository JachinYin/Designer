package com.jachin.des.service;

import com.jachin.des.def.DataDef;
import com.jachin.des.entity.CashFlow;
import com.jachin.des.entity.Designer;
import com.jachin.des.entity.DesignerAudit;
import com.jachin.des.entity.SearchArg;
import com.jachin.des.mapper.CashFlowMapper;
import com.jachin.des.mapper.DesignerAuditMapper;
import com.jachin.des.mapper.DesignerMapper;
import com.jachin.des.mapper.provider.DesignerSql;
import com.jachin.des.util.CommTool;
import com.jachin.des.util.CurrentUser;
import com.jachin.des.util.ResParam;
import com.jachin.des.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jachin
 * @since 2019/3/16 15:00
 */
@Service
public class DesignerService {

    @Autowired
    DesignerMapper designerMapper;

    @Autowired
    DesignerAuditMapper designerAuditMapper;

    @Autowired
    CashFlowMapper cashFlowMapper;

    DesignerSql designerSql = new DesignerSql();

    // 获取某个用户的取现总数
    private int getWithdrawCount(int aid, List<CashFlow> list){
        int sum = 0;
        for (CashFlow item : list) {
            if(item.getAid() == aid){
                sum += item.getPrice();
            }
        }
        return sum;
    }

    // 获取分佣管理中的数据
    public Response getCashDesList(SearchArg searchArg) {
        searchArg.setCompColumns("totalPrice", true);
        searchArg.setStatus(DataDef.DesignerStatus.PASS);   // 只查看通过的设计师
        // 拿到设计师所有的信息
        List<Designer> designerList = designerMapper.getDesignerList(searchArg);

        // 拿所有设计师的提现记录
        SearchArg cashSearchArg = new SearchArg();
        cashSearchArg.setType(DataDef.CashFlag.WITHDRAW);
        List<CashFlow> cashFlowList = cashFlowMapper.getCashFlowList(cashSearchArg);

        // 格式化成需要展示的数据
        List<ResParam> showList = new ArrayList<>();
        for (Designer item : designerList) {
            ResParam newItem = new ResParam();
            newItem.put("aid", item.getAid());
            newItem.put("nickName", item.getNickName());
            newItem.put("totalPrice", item.getTotalPrice());
            newItem.put("balance", item.getBalance());
            newItem.put("withdraw", getWithdrawCount(item.getAid(), cashFlowList));
            showList.add(newItem);
        }

        Response response = new Response(true, "获取分佣列表数据");
        response.setData(new ResParam("list", showList));
        return response;
    }

    public Response getDesignerInfo(SearchArg searchArg){
        int aid = searchArg.getAid();
        if(aid < 1) {
            aid = CurrentUser.getCurrentAid();
            searchArg.setAid(aid);
        }
        if(aid < 1) return new Response(false, "账户ID错误。");

        Designer designer = designerMapper.getDesigner(searchArg);
        if (designer == null) return new Response(false, "获取信息失败。");

        Response response = new Response(true, "获取信息成功");
        ResParam resParam = new ResParam("desData", designer);
        response.setData(resParam);
        return response;
    }


    // =====基础服务=====
    public Response getDesigner(SearchArg searchArg){
        int aid = searchArg.getAid();
        if(aid < 1){
            return new Response(false, "Aid错误");
        }
        Designer designer = designerMapper.getDesigner(searchArg);


        // 获取设计师审核表的记录
        searchArg.setCompColumns("time", true);
        List<DesignerAudit> list = designerAuditMapper.getDesignerAuditList(searchArg);

        Response response = new Response(true, "获取设计师信息");

        ResParam resParam = new ResParam();
        int rt = CommTool.mergeResParam(resParam, designer);
        if(rt != 0) response.setMsg("合并Param出错");
        resParam.put("list", list);
        response.setData(resParam);
        return response;
    }

    public Response getDesignerList(SearchArg searchArg){

        // 获取设计师表的记录
        List<Designer> designerList = designerMapper.getDesignerList(searchArg);

        ResParam resParam = new ResParam();
        resParam.put("list", designerList);

        Response response = new Response(true, "获取设计师列表");
        response.setData(resParam);


        return response;
    }

    /**
     * 根据账户Id更新设计师信息
     */
    public Response setDesigner(Designer designer){
        int aid = designer.getAid();
        if (aid == 0){
            aid = CurrentUser.getCurrentAid();
            designer.setAid(aid);
        }
        if(aid < 1){
            return new Response(false, "操作失败，账户ID错误。");
        }

        Response response = new Response(true, "更新成功");

        int rt = designerMapper.setDesigner(designer);
        if(rt == 0) return new Response(false, "更新失败。");

        System.out.println(designer);
        ResParam resParam = new ResParam();
        resParam.put("sql", designerSql.setDesigner(designer));
        response.setData(resParam);

        return response;
    }

    /**
     * 添加设计师（只有在用户注册的时候使用这个方法）
     */
    public Response addDesigner(Designer designer){
        Response response = new Response(true, "操作成功");
        if(designer.getAid() < 1) return new Response(false, "操作失败，账户ID错误");
        int rt = designerMapper.addDesigner(designer);
        if(rt  == 0) return new Response(false, "操作失败,code=1。");
        ResParam resParam = new ResParam();
        resParam.put("sql", designerSql.addDesigner(designer));
        response.setData(resParam);
        return response;
    }

    /**
     * 根据账户Id删除设计师（用户注销操作，实际上是更改设计师的状态值）
     */
    public Response delDesigner(SearchArg searchArg){
        int aid = searchArg.getAid();
        if(aid < 1) return new Response(false, "操作失败，账户Id错误");

        Response response = new Response(true, "操作成功");

        int rt = designerMapper.delDesigner(searchArg);
        if(rt == 0) return new Response(false, "操作失败");

        ResParam resParam = new ResParam();
        resParam.put("sql", designerSql.delDesigner(searchArg));
        response.setData(resParam);

        return response;
    }
}
