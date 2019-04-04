package com.jachin.des.controller;

import com.jachin.des.entity.SearchArg;
import com.jachin.des.service.DesignerAuditService;
import com.jachin.des.service.DesignerService;
import com.jachin.des.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    // 分佣管理
    @GetMapping("/getCashDesList")
    public Response getCashDesList(SearchArg searchArg){
        return designerService.getCashDesList(searchArg);
    }

    // 获取设计师审核列表
    @GetMapping("/getDesignerAuditList")
    public Response getDesignerList(SearchArg searchArg){
        return designerAuditService.getDesignerAuditList(searchArg);
    }

    // 根据 aid 获取设计师信息【用于设计师审核详情】
    @GetMapping("/getDesigner")
    public Response getDesigner(SearchArg searchArg){
        return designerService.getDesigner(searchArg);
    }

    //

}
