package com.jachin.des.service;

import com.jachin.des.entity.SearchArg;
import com.jachin.des.entity.User;
import com.jachin.des.util.CommTool;
import com.jachin.des.util.Response;

/**
 * @author Jachin
 * @since 2019/3/28 21:29
 */
public class UserService {


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

    public Response getUser(SearchArg searchArg){

        Response response = new Response(true);
        return response;
    }

}
