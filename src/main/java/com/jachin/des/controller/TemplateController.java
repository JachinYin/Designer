package com.jachin.des.controller;

import com.jachin.des.mapper.TemplateMapper;
import com.jachin.des.pojo.Template;
import com.jachin.des.util.Param;
import com.jachin.des.web.WebDesigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Jachin
 * @since 2019/3/8 15:06
 */
@Controller
public class TemplateController {

    @Autowired
    TemplateMapper templateMapper;

    @ResponseBody
    @GetMapping("/getTemplateList")
    public String getTemplateList(){
        List<Template> templateList = templateMapper.findAll();
        Param param = new Param();
        return param.setObject("list", templateList).setString("msg", "测试mybatis").toJson();
    }


    @GetMapping("/addTemplate")
    public String addTemplate(@RequestParam("template")String template){
        Param param = Param.parseParam(template);

        System.out.println(param.toJson());
//
//        if(param.getBoolean("success")){
//            return WebDesigner.getRetParam(false,"add template error;参数错误").toJson();
//        }

        try{
            Template templatePojo = new Template();
            templatePojo.setAid(param.getInt("aid"));
            templatePojo.setTempId(param.getInt("tempId"));
            templatePojo.setName(param.getString("name"));
            templatePojo.setTime(param.getString("time"));
            templatePojo.setStatus(param.getInt("status"));
            templatePojo.setPrice(param.getDouble("price"));
            templateMapper.addTemplate(templatePojo);
        }catch (Exception e){
            return WebDesigner.getRetParam(false,"add template error;参数错误").toJson();
        }

        return WebDesigner.getRetParam(true, "插入新的审核记录").toJson();
    }
}
