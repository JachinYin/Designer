package com.jachin.des.filter;

import com.jachin.des.web.WebDesigner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Jachin
 * @since 2019/3/17 11:26
 */
public class CheckLoginFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(CheckLoginFilter.class);

    //不需要登录就可以访问的路径(比如:注册登录等)
    private String[] includeUrls = new String[]{"/login","register"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        HttpSession session = httpRequest.getSession();
        String uri = httpRequest.getRequestURI();

        boolean isLogin = WebDesigner.checkLogin(httpRequest);

        boolean needFilter = isNeedFilter(uri);

        if(!needFilter){    // 不需要过滤则直接通过，到下一个过滤器
            filterChain.doFilter(httpRequest,httpResponse);
        }
        else{
            if(session != null && session.getAttribute("U_SESSION") != null){
                filterChain.doFilter(httpRequest, httpResponse);
            }
            else if(isLogin){
                filterChain.doFilter(httpRequest, httpResponse);
            }else{
                // 后端服务器，所有的请求都是 REST 风格的，也就是，都为 AJAX 请求
                httpResponse.setContentType("application/json;charset=utf-8;");
                httpResponse.getWriter().write("{\"success\": false, \"msg\": \"用户未登陆!\", \"code\": 101}");
            }
        }
    }

    /**
     * 定义一个判断是否需要过滤的方法
     * @param uri 传入需要判断的URI
     * @return 返回布尔值
     */
    public boolean isNeedFilter(String uri) {

        for (String includeUrl : includeUrls) {
            if(includeUrl.equals(uri)) {
                return false;
            }
        }

        return true;
    }
}
