package com.jachin.des.controller;

import com.jachin.des.entity.SearchArg;
import com.jachin.des.entity.Template;
import com.jachin.des.service.TemplateAuditService;
import com.jachin.des.service.TemplateService;
import com.jachin.des.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

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

    @RequestMapping("/upload")
    public Response uploadImg(@RequestParam(required = false) MultipartFile file,HttpServletRequest request){
        return templateService.uploadImg(file, request);
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

