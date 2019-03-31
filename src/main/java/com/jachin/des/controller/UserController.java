package com.jachin.des.controller;

import com.jachin.des.entity.SearchArg;
import com.jachin.des.entity.User;
import com.jachin.des.service.UserService;
import com.jachin.des.util.ResParam;
import com.jachin.des.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jachin
 * @since 2019/3/6 14:55
 */
@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Response login(SearchArg searchArg, HttpServletResponse httpResponse){
        // 用户登陆
        Response response = userService.userLogin(searchArg);
        if(!response.isSuccess()) return new Response(false,response.getMsg());

        // 验证通过，获取token写入Cookie
        ResParam data = (ResParam) response.getData();
        String token = (String) data.get("TOKEN");

        Cookie t_cookie = new Cookie("TOKEN", token);
        httpResponse.addCookie(t_cookie);

        response.setData(new ResParam("TOKEN", token));
        return response;
    }

    @GetMapping("/register")
    public Response register(User user){
        return userService.userRegister(user);
    }

    @GetMapping("/logout")
    public Response logout(HttpServletResponse httpResponse){
        Cookie t_cookie = new Cookie("TOKEN", "");
        t_cookie.setMaxAge(0);
        httpResponse.addCookie(t_cookie);
        return new Response(true);
    }

}
