package com.jachin.des.controller;

import com.jachin.des.util.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jachin
 * @since 2019/3/6 14:55
 */
@Controller
public class UserController {

    @GetMapping("/login")
    public String login(
            @RequestParam(value = "name",required = false)String name,
            @RequestParam(value = "password", required = false) String password,
            Model model
    ){
        if(getUser().equals(name)) {
            if (getPwd().equals(password)) {
                model.addAttribute("name", name);
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                request.getSession().setAttribute("user", name);
                return "redirect:home";
            }
        }
        return "/user/login";
    }

    @ResponseBody
    @GetMapping("/loginValidate")
    public String loginValidate(
            @RequestParam(value = "name",required = false)String name,
            @RequestParam(value = "password", required = false) String password,
            Model model
    ){
        if (!getUser().equals(name))
            return getRetParam(false, "用户名不存在").setInt("type", 1).toJson();
        if (!getPwd().equals(password))
            return getRetParam(false, "密码错误").setInt("type", 2).toJson();

        model.addAttribute("name", name);
        return getRetParam(true, "验证通过").toJson();
    }

    @GetMapping("/logout")
    public String logout(Model model
    ){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.getSession().setAttribute("user", null);
        return "redirect:home";
    }



    private String getUser(){
        return "jachin";
    }
    private String getPwd(){
        return "1234567";
    }

    private Param getRetParam(boolean success, String msg){
        Param res = new Param();
        res.setBoolean("success", success);
        res.setString("msg", msg);
        return res;
    }
}
