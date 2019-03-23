package com.jachin.des.controller;

import com.jachin.des.entity.SearchArg;
import com.jachin.des.entity.TemplateAudit;
import com.jachin.des.mapper.TemplateAuditMapper;
import com.jachin.des.service.TemplateAuditService;
import com.jachin.des.util.ResParam;
import com.jachin.des.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jachin
 * @since 2019/3/8 15:06
 */
@RestController
public class TemplateAuditController {
    private static final Logger log = LoggerFactory.getLogger(TemplateAuditController.class);

    @Autowired
    TemplateAuditService templateAuditService;

    @GetMapping("/getTemplateAuditList")
    public Response getTemplateListBySearch(SearchArg searchArg){
        return templateAuditService.getTemplateAuditList(searchArg);
    }

    /**
     * 用于模板审核表。点击查看时，返回展示数据信息
     * 根据模板ID来获取数据，包括设计师信息，该模板所有审核记录，以及模板信息
     * @param templateAudit 模板对象，会自动映射相同的字段到对象中
     * @return ResParam
     */
    @GetMapping("/getTempAuditById")
    public Response getTempById(TemplateAudit templateAudit){
        return templateAuditService.getTempById(templateAudit.getTempId());
    }

    // 模板通过
    @GetMapping("/tempPass")
    public Response tempPass(TemplateAudit templateAudit){
        return templateAuditService.tempPass(templateAudit.getTempId(), templateAudit.getPrice());
    }

    // 模板打回
    @GetMapping("/tempRefuse")
    public Response tempRefuse(TemplateAudit templateAudit){
        return templateAuditService.tempRefuse(templateAudit.getTempId(), templateAudit.getReason());
    }


    // ================设计师前台

    @GetMapping("/addTemplate")
    public Response addTemplate(TemplateAudit templateAudit){
        return templateAuditService.addTemplateAudit(templateAudit);
    }
}
