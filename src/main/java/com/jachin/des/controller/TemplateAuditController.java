package com.jachin.des.controller;

import com.jachin.des.entity.SearchArg;
import com.jachin.des.entity.TemplateAudit;
import com.jachin.des.service.TemplateAuditService;
import com.jachin.des.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    // 业务逻辑请求

    /** done
     * 用于模板审核表。点击查看时，返回展示数据信息
     * 根据模板ID来获取数据，包括设计师信息，该模板所有审核记录，以及模板信息
     */
    @GetMapping("/getAuditShowData")
//    @GetMapping("/getTempAuditById")
    public Response getTempById(SearchArg searchArg){
        return templateAuditService.getAuditShowData(searchArg);
    }

    @GetMapping("/doTemplateAudit/{type}")
    public Response passOrRefuseTemplate(TemplateAudit templateAudit, @PathVariable("type")String type){
        return templateAuditService.doTemplateAudit(templateAudit, type);
    }

    // =====基础请求=====

    @GetMapping("/getTemplateAuditList")// done
    public Response getTemplateListBySearch(SearchArg searchArg){
        return templateAuditService.getTemplateAuditList(searchArg);
    }

    @GetMapping("/addTemplateAudit")
    public Response addTemplate(TemplateAudit templateAudit){
        return templateAuditService.addTemplateAudit(templateAudit);
    }

}
