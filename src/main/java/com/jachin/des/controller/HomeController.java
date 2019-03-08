package com.jachin.des.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Jachin
 * @since 2019/3/6 12:44
 */
@Controller
public class HomeController {

    /**
     * 全部基于 Spring Boot给 Thymeleaf的默认配置
     * 所以下面会跳转到 classpath:/templates/home.html 页面
     */
    @GetMapping("home")
    public String goHome(Model model) {

//        if(WebDesigner.checkLogin())
//            return "redirect:login";

        model.addAttribute("active", "active0");
        return "home";
    }

    @GetMapping("/error")
    public String goError() {

//        if(WebDesigner.checkLogin())
//            return "redirect:login";
        return "error";
    }

    @GetMapping("/tempList")
    public String gotoTemplate(Model model){
        model.addAttribute("active", "active0");
        return "home";
    }
    @GetMapping("/desList")
    public String gotoDesignerList(Model model){
        model.addAttribute("active", "active1");
        return "home";
    }
    @GetMapping("/cashTemp")
    public String gotoCashTemp(Model model){
        model.addAttribute("active", "active2");
        return "home";
    }
    @GetMapping("/cashDes")
    public String gotoCashDes(Model model){
        model.addAttribute("active", "active3");
        return "home";
    }

}
