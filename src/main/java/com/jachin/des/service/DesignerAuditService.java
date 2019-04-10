package com.jachin.des.service;

import com.jachin.des.def.DataDef;
import com.jachin.des.entity.Designer;
import com.jachin.des.entity.DesignerAudit;
import com.jachin.des.entity.SearchArg;
import com.jachin.des.mapper.DesignerAuditMapper;
import com.jachin.des.mapper.DesignerMapper;
import com.jachin.des.mapper.provider.DesignerAuditSql;
import com.jachin.des.util.CommTool;
import com.jachin.des.util.CurrentUser;
import com.jachin.des.util.ResParam;
import com.jachin.des.util.Response;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jachin
 * @since 2019/3/22 20:50
 */
@Service
public class DesignerAuditService {

    @Resource
    private DesignerMapper designerMapper;

    @Resource
    private DesignerAuditMapper designerAuditMapper;

    DesignerAuditSql sql = new DesignerAuditSql();

    @Transactional // 设计师审核逻辑
    public Response doDesignerAudit(DesignerAudit designerAudit, String typeName) {
        int status = DataDef.getDesignerStatus(typeName);
        if(!(status == DataDef.DesignerStatus.PASS || status == DataDef.DesignerStatus.BACK))
            return new Response(false, "请求参数错误");

        int aid = designerAudit.getAid();
        if(aid < 1) return new Response(false, "账号ID错误。");

        SearchArg searchArg = new SearchArg();
        searchArg.setAid(aid);

        // 1.插入审核记录
        searchArg.setCompColumns("time", true);
        List<DesignerAudit> designerAuditList = designerAuditMapper.getDesignerAuditList(searchArg);
        DesignerAudit lastDesAudit = new DesignerAudit();
        if(designerAuditList != null) lastDesAudit = designerAuditList.get(0);
        lastDesAudit.setStatus(status);
        if(status == DataDef.DesignerStatus.PASS) lastDesAudit.setReason("");
        else lastDesAudit.setReason(designerAudit.getReason());
        int rt = designerAuditMapper.addDesignerAudit(lastDesAudit);
        if(rt == 0) return new Response(false);

        // 2.更改设计师状态
        Designer designer = new Designer();
        designer.setAid(aid);
        designer.setStatus(status);
        rt = designerMapper.setDesigner(designer);
        if(rt == 0) return new Response(false);
        return new Response(true);
    }

    /**
     * 审核设计师
     * 对于当前的业务，只有两种可能：
     * 1.基于通过业务的修改
     * 2.基于打回业务的修改
     * 然而最终，都是对审核记录表的新增操作
     *
    public Response doDesignerAudit(DesignerAudit designerAudit, String typeName) {
        int type = DataDef.getTemplateStatus(typeName);
        if(type == 0) return new Response(false, "请求参数错误");
        int aid = designerAudit.getAid();
        SearchArg searchArg = new SearchArg();
        searchArg.setAid(aid);
        List<DesignerAudit> designerAuditList = designerAuditMapper.getDesignerAuditList(searchArg);
        DesignerAudit lastDesignerAudit;
        try {
            lastDesignerAudit = designerAuditList.get(0);
        }catch (Exception e){
            return new Response(false, "系统错误，请联系管理员。");
        }

        if(type == DataDef.DesignerStatus.BACK){
            lastDesignerAudit.setStatus(DataDef.DesignerStatus.BACK);
        }
        else if(type == DataDef.DesignerStatus.PASS){
            lastDesignerAudit.setStatus(DataDef.DesignerStatus.PASS);
        }

        int rt = designerAuditMapper.addDesignerAudit(lastDesignerAudit);
        if(rt == 0) return new Response(false);
        return new Response(true);
    }
     */

    // =====基础查改增删=====

    public Response getDesignerAudit(SearchArg searchArg){
        Response response = new Response(true, "获取成功");
        DesignerAudit designerAudit = designerAuditMapper.getDesignerAudit(searchArg);
        response.setData(new ResParam("designerAuditData",  designerAudit));
        return response;
    }

    public Response getDesignersAuditList(SearchArg searchArg){
        searchArg.setCompColumns("time", true);
        List<DesignerAudit> list = designerAuditMapper.getDesignerAuditList(searchArg);

        Response response = new Response(true, "获取成功");
        ResParam resParam = new ResParam();
        resParam.put("list", list);
        resParam.put("designerAuditSql", sql.getDesignerAuditList(searchArg));
        response.setData(resParam);
        return response;
    }

    public Response getDesignerAuditList(SearchArg searchArg){
        int aid = searchArg.getAid();
        if(aid < 1) searchArg.setAid(CurrentUser.getCurrentAid());
        searchArg.setCompColumns("time", true);
        List<DesignerAudit> list = designerAuditMapper.getDesignerAuditList(searchArg);

        Response response = new Response(true, "获取成功");
        response.setData(new ResParam("list", list));
        return response;
    }

    public Response setDesignerAudit(DesignerAudit designerAudit){
        if(designerAudit.getId() == 0) return new Response(false, "操作失败，ID错误。");
        int rt = designerAuditMapper.setDesignerAudit(designerAudit);
        if(rt == 0) return new Response(false);
        return new Response(true);
    }

    public Response addDesignerAudit(DesignerAudit designerAudit){
        int aid = designerAudit.getAid();
        if(aid < 1) designerAudit.setAid(CurrentUser.getCurrentAid());
        if(designerAudit.getAid() < 1) return new Response(false, "操作失败，账户ID错误");
        if(!CommTool.isNotBlank(designerAudit.getNickName())) return new Response(false, "操作失败，设计师昵称不能为空。");

        designerAudit.setStatus(DataDef.DesignerStatus.WAIT);

        int rt = designerAuditMapper.addDesignerAudit(designerAudit);
        if(rt == 0) return new Response(false);

        Designer designer = new Designer();
        designer.setAid(designerAudit.getAid());
        designer.setStatus(DataDef.DesignerStatus.WAIT);
        rt = designerMapper.setDesigner(designer);
        if (rt == 0) return new Response(false, "更改设计师状态失败，请联系管理员。");

        Response response = new Response(true);
        response.setData(new ResParam("designerAuditSql", sql.addDesignerAudit(designerAudit)));
        return response;
    }

    public Response delDesignerAudit(SearchArg searchArg){
        if(searchArg.getAid() < 1) return new Response(false, "操作失败，账户ID错误。");

        int rt = designerAuditMapper.delDesignerAudit(searchArg);
        if(rt == 0) return new Response(false);

        Response response = new Response(true);
        response.setData(new ResParam("designerAuditSql", sql.delDesignerAudit(searchArg)));
        return response;
    }
}
