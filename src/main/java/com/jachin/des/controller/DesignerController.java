package com.jachin.des.controller;

import com.jachin.des.entity.Designer;
import com.jachin.des.entity.SearchArg;
import com.jachin.des.service.DesignerAuditService;
import com.jachin.des.service.DesignerService;
import com.jachin.des.util.CommTool;
import com.jachin.des.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Jachin
 * @since 2019/3/16 14:58
 */
@RestController
public class DesignerController {

    @Autowired
    DesignerService designerService;

    @Autowired
    DesignerAuditService designerAuditService;

    // 设计师前台获取方法
    @GetMapping("/getDesignerInfo")
    public Response getDesignerInfo(SearchArg searchArg){
        return designerService.getDesignerInfo(searchArg);
    }
    // 设计师前台保存方法
    @GetMapping("/setDesignerInfo")
    public Response setDesignerInfo(@Valid Designer designer, BindingResult bindingResult){
//        if(bindingResult.hasErrors()){
//            return new Response(false, bindingResult.getFieldError().getDefaultMessage());
//        }
        Response response = CommTool.validateArg(bindingResult);
        if(response != null) return response;
        return designerService.setDesigner(designer);
    }
}
