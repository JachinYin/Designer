package com.jachin.des.controller;

import com.jachin.des.entity.SearchArg;
import com.jachin.des.entity.Template;
import com.jachin.des.service.TemplateAuditService;
import com.jachin.des.service.TemplateService;
import com.jachin.des.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Jachin
 * @since 2019/3/18 23:07
 */
@RestController
public class TemplateController {

    @Autowired
    TemplateAuditService templateAuditService;
    @Autowired
    TemplateService templateService;

    @RequestMapping("/upload/{imgType}")
    public Response uploadImg(@RequestParam(required = false) MultipartFile file, @PathVariable("imgType")int imgType){
        return templateService.uploadImg(file, imgType);
    }



    // 前台获取模板列表数据
    @GetMapping("/getTemplateList")
    public Response getTemplateList(SearchArg searchArg){
        return templateService.getTemplateList(searchArg);
    }

    @GetMapping("/getTemplate")
    public Response getTemplate(SearchArg searchArg){
        return templateService.getTemplate(searchArg);
    }

    @GetMapping("/setTemplate")
    public Response setTemplate(Template template){
        return templateService.setTemplate(template);
    }
    @GetMapping("/addTemplate")
    public Response addTemplate(Template template){
        return templateService.addTemplate(template);
    }

    @GetMapping("delTemplate")
    public Response delTemplate(SearchArg searchArg){
        return templateService.delTemplate(searchArg.getTempId());
    }

}

