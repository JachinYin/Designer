package com.jachin.des.filter;

import com.jachin.des.config.JwtProperties;
import com.jachin.des.util.CommTool;
import com.jachin.des.util.CurrentUser;
import com.jachin.des.util.JwtUtils;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Jachin
 * @since 2019/3/30 17:06
 */
public class AuthFilter extends OncePerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Resource
    private JwtUtils jwtTokenUtil;

    @Resource
    private JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 确认请求路径是否为 JWT 授权路径，如果是，则跳过 JWT 的授权验证
        if (request.getServletPath().equals("/" + jwtProperties.getAuthPath())) {
            chain.doFilter(request, response);
            return;
        }

        // 验证请求路径是否包含在忽略列表中，如果是，也跳过 JWT 授权验证
        String ignoreUrl = jwtProperties.getIgnoreUrl();
        String[] ignoreUrls = ignoreUrl.split(",");
        for (String url : ignoreUrls) {
            // 判断请求路径前缀是否与忽略列表一致
            if (request.getServletPath().startsWith(url)){
                chain.doFilter(request, response);
                return;
            }
        }

        final String token = request.getHeader(jwtProperties.getHeader());
        if (CommTool.isNotBlank(token)) {
            //验证token是否过期,包含了验证jwt是否正确
            try {
                boolean flag = jwtTokenUtil.isTokenExpired(token);
                if (flag) {
                    response.getWriter().write("{\"success\": false, \"msg\": \"Token异常!\", \"code\": 101}");
                    return;
                }
            } catch (JwtException e) {
                //有异常就是token解析失败
                response.getWriter().write("{\"success\": false, \"msg\": \"token解析失败!\", \"code\": 101}");
                return;
            }
        } else {
            //header没有TOKEN字段
            response.getWriter().write("{\"success\": false, \"msg\": \"header无TOKEN!\", \"code\": 101}");
            return;
        }
        String aid = jwtTokenUtil.getAidFromToken(token);
        if(CommTool.isNotBlank(aid)){
            int aidInt = 0;
            try {
                aidInt = Integer.parseInt(aid);
            }catch (Exception ignore){}

            CurrentUser.setCurrentAid(aidInt);
            logger.info("-----当前用户AID----"+aid);
        }

        chain.doFilter(request, response);
    }
}
