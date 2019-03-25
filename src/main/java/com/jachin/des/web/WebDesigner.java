package com.jachin.des.web;

import com.jachin.des.util.JacParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Jachin
 * @since 2019/3/6 17:59
 */
public class WebDesigner {
    private static final Logger log = LoggerFactory.getLogger(WebDesigner.class);

    public static boolean checkLogin(HttpServletRequest httpRequest){

        Cookie[] cookies = httpRequest.getCookies();
        String name = "";
        String token  = "";
        if(cookies!=null)
            for(Cookie item : cookies){
                if("name".equals(item.getName())){
                    name = item.getValue();
                }
                if("TOKEN".equals(item.getName())){
                    token = item.getValue();
                }
            }
        log.info("登陆信息="+name + token);
        // TODO 正确的验证信息
//        return !name.isEmpty() && !token.isEmpty();
        return true;
    }

    public static JacParam getRetParam(boolean success, String msg){
        JacParam jacParam = new JacParam();
        return jacParam.setBoolean("success", success).setString("msg", msg);
    }
}
