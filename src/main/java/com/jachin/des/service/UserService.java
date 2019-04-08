package com.jachin.des.service;

import com.jachin.des.entity.Designer;
import com.jachin.des.entity.SearchArg;
import com.jachin.des.entity.User;
import com.jachin.des.mapper.DesignerMapper;
import com.jachin.des.mapper.UserMapper;
import com.jachin.des.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Jachin
 * @since 2019/3/28 21:29
 */
@Service
public class UserService {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserMapper userMapper;

    @Autowired
    DesignerMapper designerMapper;

    // 用户登陆
    public Response userLogin(SearchArg searchArg, HttpServletResponse httpResponse){
        if(!CommTool.isNotBlank(searchArg.getUserName())) return new Response(false, "用户名不能为空。");
        if(!CommTool.isNotBlank(searchArg.getPassword())) return new Response(false, "密码不能为空。");

        User user = userMapper.getUser(searchArg);
        if(user == null) return new Response(false, "用户名或密码错误。");

        // 验证通过，发放token
        final String randomKey = jwtUtils.getRandomKey();
        // 为客户端生成 jwt
        final String token = jwtUtils.generateToken(String.valueOf(user.getAid()), randomKey);
        if(token.isEmpty()) return new Response(false, "系统错误。");
        user.setToken(token);
        // 保存token至sql
        int rt = userMapper.setUser(user);
        if(rt == 0) return new Response(false);

        // 验证通过，获取token写入Cookie
//        ResParam data = (ResParam) response.getData();
//        String token = (String) data.get("TOKEN");
//        Cookie t_cookie = new Cookie("TOKEN", token);
//        httpResponse.addCookie(t_cookie);

        Response response = new Response(true, "登陆成功");
        ResParam resParam = new ResParam("TOKEN", token);
        resParam.put("AID", user.getAid());
        response.setData(resParam);
        return response;
    }

    // 用户登出
    public Response userLogout(){
        int aid = CurrentUser.getCurrentAid();
        return new Response(true);
    }

    // 用户注册服务
    public Response userRegister(User user){
        if(!CommTool.isNotBlank(user.getUserName())) return new Response(false, "用户名不能为空。");
        if(!CommTool.isNotBlank(user.getPassword())) return new Response(false, "密码不能为空。");
        if(isExistUser(user.getUserName())) return new Response(false, "用户名已存在。");

        int rt = userMapper.addUser(user);
        if(rt == 0) return new Response(false);

        // 获取新注册的用户AID
        SearchArg searchArg = new SearchArg();
        searchArg.setUserName(user.getUserName());
        User newUser = userMapper.getUser(searchArg);

        // 在设计师表插入新注册的用户
        Designer designer = new Designer();
        designer.setAid(newUser.getAid());
        rt = designerMapper.addDesigner(designer);
        if(rt == 0) return new Response(false);

        Response response = new Response(true);
        response.setData(new ResParam("newAid", user.getAid()));
        return response;
    }

    /**
     * 判断用户名是否存在
     */
    public boolean isExistUser(String userName){
        SearchArg searchArg = new SearchArg();
        searchArg.setUserName(userName);
        User user = userMapper.getUser(searchArg);
        return user != null;
    }


    // 基础

    public Response addUser(User user){
        if(CommTool.isNotBlank(user.getUserName())) return new Response(false, "用户名不能为空");
        if(CommTool.isNotBlank(user.getPassword())) return new Response(false, "密码不能为空");
        Response response = new Response(true);
        return response;
    }

    public Response setUser(User user){
        if(CommTool.isNotBlank(user.getUserName())) return new Response(false, "用户名不能为空");
        if(CommTool.isNotBlank(user.getPassword())) return new Response(false, "密码不能为空");
        if(user.getAid() == 0) return new Response(false, "账户ID为空");
        Response response = new Response(true);
        return response;
    }

    public User getUser(SearchArg searchArg){
        return userMapper.getUser(searchArg);
    }
}
