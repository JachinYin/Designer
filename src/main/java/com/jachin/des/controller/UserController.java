package com.jachin.des.controller;

import com.jachin.des.entity.SearchArg;
import com.jachin.des.entity.User;
import com.jachin.des.service.UserService;
import com.jachin.des.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return userService.userLogin(searchArg);
    }

    @PostMapping("/authLogin")
    public Response login_auth(SearchArg searchArg, HttpServletResponse httpResponse){
        return userService.authLogin(searchArg);
    }

    @GetMapping("/register")
    public Response register(User user){
        return userService.userRegister(user);
    }

    @GetMapping("/logout")
    public Response logout(HttpServletResponse httpResponse){
        return userService.userLogout();
    }

}
