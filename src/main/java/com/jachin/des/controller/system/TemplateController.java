package com.jachin.des.controller.system;

import com.jachin.des.mapper.TemplateMapper;
import com.jachin.des.pojo.Template;
import com.jachin.des.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    TemplateMapper templateMapper;

    @GetMapping("/getTemplateList")
    public Response getTemplateListBySearch(){
        List<Template> templateList = templateMapper.findAll();
        Response response = new Response(true, "获取模板数据");
        HashMap<String, Object> list = new HashMap<>();
        list.put("list", templateList);
        response.setOther(list);
        return response;
    }

    @GetMapping("/updateName")
    public Response update(){
        Response response;
        Template template = new Template();
//        template.setName("新年");
        template.setDesigner("柒夕影3");
        template.setTime("2017-02-15 18:12:25");
        template.setPrice(26.9);
        template.setAid(1);
        template.setTempId(1);
        try {
            templateMapper.updateName(template);
        } catch (Exception e) {
            response = new Response(false, "更新失败");
            log.error("模板审核表，更新失败; aid="+ template.getAid() + "; "+ e.getMessage());
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
        Template template = new Template();
        template.setId(16);
        try {
            templateMapper.deleteById(template);
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
        Template template1 = new Template();
        template1.setAid(1);
        template1.setTempId(17);
        template1.setName("新年祝词");
        template1.setDesigner("柒夕影");
        template1.setTime("2019-03-06 18:25:32");
        template1.setStatus(2);
        template1.setPrice(23.6);
        try {
            templateMapper.addTemplate(template1);
        }catch (Exception e){
            response = new Response(false, "新增失败");
            System.out.println(e.getMessage());
            return response;
        }
        response = new Response(true, "插入成功");
        return response;
    }
}
