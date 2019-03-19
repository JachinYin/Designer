package com.jachin.des.controller;

import com.jachin.des.util.ResParam;
import com.jachin.des.util.Response;
import com.jachin.des.web.WebDesigner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

/**
 * @author Jachin
 * @since 2019/3/6 14:55
 */
@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);


    @GetMapping("/login")
    public Response login(
            @RequestParam(value = "name",required = false)String name,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "rememberMe", required = false) Boolean rememberMe,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse
    ){
        Response response;

        if(WebDesigner.checkLogin(httpRequest)){
            return new Response(true, "登陆成功");
        }

        if(!getUser().equals(name))
            return new Response(false, "用户名错误");

        if(!getPwd().equals(password))
            return new Response(false, "密码错误");
        int i = new Random().nextInt(1000);
        Cookie t_cookie = new Cookie("TOKEN", "FAISCO" +i);
        Cookie n_cookie = new Cookie("name", name);
        t_cookie.setMaxAge(15 * 60);
        n_cookie.setMaxAge(15 * 60);
//        t_cookie.setSecure(true);
        httpResponse.addCookie(t_cookie);
        httpResponse.addCookie(n_cookie);
        httpRequest.getSession().setAttribute("U_SESSION", name);
        response = new Response(true, "登陆成功");
        ResParam resParam = new ResParam();
        resParam.put("TOKEN", "FAISCO");
        resParam.put("name", name);
        response.setData(resParam);
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
        // 用户注销
//        request.getSession().removeAttribute("U_SESSION"); // 删除SESSION 属性（不推荐）
        request.getSession().invalidate(); // 消除整个session（推荐）
        return "redirect:login";
    }



    private String getUser(){
        return "jachin";
    }
    private String getPwd(){
        return "123123";
    }
}
