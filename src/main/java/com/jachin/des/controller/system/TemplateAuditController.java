package com.jachin.des.controller.system;

import com.jachin.des.entity.TemplateAudit;
import com.jachin.des.service.TemplateService;
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
    TemplateService  templateService;



    @GetMapping("/getTemplateAuditList")
    public Response getTemplateListBySearch(TemplateAudit templateAudit){
        return templateService.getTempList(templateAudit);
    }

    /**
     * 用于模板审核表。点击查看时，返回展示数据信息
     * 根据模板ID来获取数据，包括设计师信息，该模板所有审核记录，以及模板信息
     * @param templateAudit 模板对象，会自动映射相同的字段到对象中
     * @return ResParam
     */
    @GetMapping("/getTempById")
    public Response getTempById(TemplateAudit templateAudit){
        return templateService.getTempById(templateAudit.getTempId());
    }

    // 模板通过
    @GetMapping("/tempPass")
    public Response tempPass(TemplateAudit templateAudit){
        return templateService.tempPass(templateAudit.getTempId(), templateAudit.getPrice());
    }

    // 模板打回
    @GetMapping("/tempRefuse")
    public Response tempRefuse(TemplateAudit templateAudit){
        return templateService.tempRefuse(templateAudit.getTempId(), templateAudit.getReason());
    }


    @GetMapping("/addTemplate")
    //@RequestParam("template")String template
    public Response addTemplate(TemplateAudit templateAudit){
        Response response;
        try {
//            templateAuditMapper.addTemplate(templateAudit1);
        }catch (Exception e){
            response = new Response(false, "新增失败");
            return response;
        }
        ResParam resParam = new ResParam();
        resParam.put("tempAudit",templateAudit);
        response = new Response(true, "插入成功");
        response.setData(resParam);
        return response;
    }
}
