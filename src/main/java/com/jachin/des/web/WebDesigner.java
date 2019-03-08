package com.jachin.des.web;

import com.jachin.des.util.Param;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jachin
 * @since 2019/3/6 17:59
 */
public class WebDesigner {

    public static boolean checkLogin(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Object user = request.getSession().getAttribute("user");
        return user != null && !((String) user).isEmpty();
    }

    public static Param getRetParam(boolean success, String msg){
        Param param = new Param();
        return param.setBoolean("success", success).setString("msg", msg);
    }
}
