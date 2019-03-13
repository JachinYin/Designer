package com.jachin.des.web;

import com.jachin.des.util.JacParam;
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

    public static JacParam getRetParam(boolean success, String msg){
        JacParam jacParam = new JacParam();
        return jacParam.setBoolean("success", success).setString("msg", msg);
    }
}
