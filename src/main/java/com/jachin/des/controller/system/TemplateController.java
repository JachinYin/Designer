package com.jachin.des.controller.system;

import com.jachin.des.entity.Designer;
import com.jachin.des.entity.Template;
import com.jachin.des.entity.TemplateAudit;
import com.jachin.des.mapper.DesignerMapper;
import com.jachin.des.mapper.TemplateAuditMapper;
import com.jachin.des.mapper.TemplateMapper;
import com.jachin.des.util.ResParam;
import com.jachin.des.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;

/**
 * @author Jachin
 * @since 2019/3/8 15:06
 */
@RestController
public class TemplateController {
    private static final Logger log = LoggerFactory.getLogger(TemplateController.class);

    @Autowired
    TemplateAuditMapper templateAuditMapper;
    @Autowired
    TemplateMapper templateMapper;
    @Autowired
    DesignerMapper designerMapper;



    @GetMapping("/getTemplateList")
    public Response getTemplateListBySearch(){
        List<TemplateAudit> templateAuditList = templateAuditMapper.getShowListTemp();

        Response response = new Response(true, "获取模板数据");
        ResParam resParam = new ResParam();
        resParam.put("list", templateAuditList);
        response.setOther(resParam);
        return response;
    }

    @GetMapping("/getTempById")
    public Response getTempById(@PathParam("tempId")int tempId){
        Template template = templateMapper.findById(tempId);
        // 获取该模板的所有审核记录
        List<TemplateAudit> tempAuditList = templateAuditMapper.getTempAuditById(tempId);
        TemplateAudit lastTempAudit = tempAuditList.get(0);

        Designer designer = designerMapper.getDesignerById(lastTempAudit.getAid());

        ResParam resParam = new ResParam();
        resParam.put("tempId", template.getTempId());
        resParam.put("aid", lastTempAudit.getAid());
        resParam.put("status", lastTempAudit.getStatus());
        resParam.put("price", lastTempAudit.getPrice());
        resParam.put("designer", lastTempAudit.getDesigner());
        resParam.put("info", template.getInfo());
        resParam.put("content", template.getContent());
        resParam.put("keyWd", template.getKeyWd());
        resParam.put("imgUrl", template.getImgUrl());
        resParam.put("title", template.getTitle());
        resParam.put("tempAuditList", tempAuditList);
        resParam.put("list", tempAuditList);

        Response response = new Response(true, "获取模板详情信息");
        response.setOther(resParam);
        return response;
    }



    @GetMapping("/updateName")
    public Response update(){
        Response response;
        TemplateAudit templateAudit = new TemplateAudit();
//        templateAudit.setName("新年");
        templateAudit.setDesigner("柒夕影3");
        templateAudit.setTime("2017-02-15 18:12:25");
        templateAudit.setPrice(26.9);
        templateAudit.setAid(1);
        templateAudit.setTempId(1);
        try {
            templateAuditMapper.updateName(templateAudit);
        } catch (Exception e) {
            response = new Response(false, "更新失败");
            log.error("模板审核表，更新失败; aid="+ templateAudit.getAid() + "; "+ e.getMessage());
            return response;
        }
        response = new Response(true, "更新成功");
        HashMap<String, Object> list = new HashMap<>();
        response.setOther(list);
        return response;
    }

    @GetMapping("/delete")
    public Response delete(){
        Response response;
        TemplateAudit templateAudit = new TemplateAudit();
        templateAudit.setId(16);
        try {
            templateAuditMapper.deleteById(templateAudit);
        }catch (Exception e){
            response = new Response(false, "删除失败");
            return response;
        }
        response = new Response(true, "删除成功");
        return response;
    }


    @GetMapping("/addTemplate")
    //@RequestParam("template")String template
    public Response addTemplate(){
        Response response;
        TemplateAudit templateAudit1 = new TemplateAudit();
        templateAudit1.setAid(1);
        templateAudit1.setTempId(17);
        templateAudit1.setDesigner("柒夕影");
        templateAudit1.setTime("2019-03-06 18:25:32");
        templateAudit1.setStatus(2);
        templateAudit1.setPrice(23.6);
        try {
            templateAuditMapper.addTemplate(templateAudit1);
        }catch (Exception e){
            response = new Response(false, "新增失败");
            System.out.println(e.getMessage());
            return response;
        }
        response = new Response(true, "插入成功");
        return response;
    }
}
