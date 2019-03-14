package com.jachin.des.controller;

import com.jachin.des.entity.Template;
import com.jachin.des.util.CommTool;
import com.jachin.des.util.ResParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jachin
 * @since 2019/3/14 9:41
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public ResParam getTest(){
        ResParam resParam = new ResParam();

//        ResParam oriParam = new ResParam();
//        oriParam.put("aid", 9858866);
//        oriParam.put("name", "jachin");
//        oriParam.put("age", 25);
//        oriParam.put("country", "China");
//        oriParam.put("province", "GD");
//        oriParam.put("city", "Gz");
        Template template = new Template();
        template.setTempId(1);
        template.setTitle("新年");
        template.setContent("我是简介");
        template.setInfo("行业信息");
        template.setImgUrl("封面图片");
        template.setKeyWd("关键字");


        resParam.put("title11", "新年dasfs");

        try {
            CommTool.mergeResParam(resParam, template);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resParam;
    }
}
