package com.jachin.des.controller;

import com.jachin.des.entity.DesignerAudit;
import com.jachin.des.entity.SearchArg;
import com.jachin.des.service.DesignerAuditService;
import com.jachin.des.service.DesignerService;
import com.jachin.des.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jachin
 * @since 2019/3/16 14:58
 */
@RestController
public class DesignerAuditController {

    @Autowired
    DesignerService designerService;

    @Autowired
    DesignerAuditService designerAuditService;

    @GetMapping("/doDesignerAudit/{type}")// 设计师审核通过
    public Response doDesignerAudit(DesignerAudit designerAudit,@PathVariable("type")String type){
        return designerAuditService.doDesignerAudit(designerAudit, type);
    }

    // 分佣管理获取列表数据
    @GetMapping("/getCashDesList")
    public Response getCashDesList(SearchArg searchArg){
        return designerService.getCashDesList(searchArg);
    }

    // 获取设计师审核列表
    @GetMapping("/getDesignersAuditList")
    public Response getDesignersList(SearchArg searchArg){
        return designerAuditService.getDesignersAuditList(searchArg);
    }

    // 前台获取设计师审核记录
    @GetMapping("/getDesignerAuditList")
    public Response getDesignerList(SearchArg searchArg){
        return designerAuditService.getDesignerAuditList(searchArg);
    }

    // 前台提交审核
    @GetMapping("/addDesignerAudit")
    public Response addDesignerAudit(DesignerAudit designerAudit){
        return designerAuditService.addDesignerAudit(designerAudit);
    }


}
