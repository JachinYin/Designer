package com.jachin.des.config;

import com.jachin.des.filter.CheckLoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 通过配置类的方式，注册登陆过滤器
 * @author Jachin
 * @since 2019/3/17 14:41
 */
@Configuration
public class CheckLoginConfig {
    @Bean
    public FilterRegistrationBean checkLoginFilter() {
        //新建过滤器注册类
        FilterRegistrationBean registration = new FilterRegistrationBean();
        // 添加我们写好的过滤器
        registration.setFilter( new CheckLoginFilter());
        // 设置过滤器的URL模式
        registration.addUrlPatterns("/*");
        registration.setOrder(5);
        return registration;
    }
}
