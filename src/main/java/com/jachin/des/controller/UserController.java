package com.jachin.des.controller;

import com.jachin.des.util.Response;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jachin
 * @since 2019/3/6 14:55
 */
@RestController
public class UserController {

    @GetMapping("/login")
    public Response login(
            @RequestParam(value = "name",required = false)String name,
            @RequestParam(value = "password", required = false) String password,
            Model model
    ){
        Response response;
        if(getUser().equals(name)) {
            if (getPwd().equals(password)) {
                model.addAttribute("name", name);
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                request.getSession().setAttribute("user", name);
                response = new Response(true, "登陆成功");
                return response;
            }
        }
        response = new Response(false, "登陆失败");
        return response;
    }

    @GetMapping("/loginValidate")
    public Response loginValidate(
            @RequestParam(value = "name",required = false)String name,
            @RequestParam(value = "password", required = false) String password,
            Model model
    ){
        Response response;
        if (!getUser().equals(name)) {
            response = new Response(false, "用户不存在");
            return response;
        }
        if (!getPwd().equals(password)) {
            response = new Response(false, "密码错误");
            return response;
        }
        model.addAttribute("name", name);
        response = new Response(true, "验证通过");
        return response;
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
}
