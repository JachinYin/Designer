package com.jachin.des.config;

import com.jachin.des.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Jachin
 * @since 2019/3/9 17:12
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        // 注册拦截器
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**");
    }
}
