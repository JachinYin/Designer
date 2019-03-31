package com.jachin.des.config;

import com.jachin.des.filter.AuthFilter;
import com.jachin.des.util.CommTool;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;

/**
 * @author Jachin
 * @since 2019/3/9 17:12
 */
@Configuration
//public class WebAppConfig implements WebMvcConfigurer {
public class WebAppConfig extends WebMvcConfigurerAdapter implements WebMvcConfigurer {

    @Bean
//    @ConditionalOnProperty(prefix = "rest", name = "auth-open", havingValue = "true", matchIfMissing = true)
    public AuthFilter jwtAuthenticationTokenFilter() {
        return new AuthFilter();
    }

    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大KB,MB
//        factory.setMaxFileSize("10MB");
        //设置总上传数据总大小
//        factory.setMaxRequestSize("10MB");
        return factory.createMultipartConfig();
    }

    /**
     * 这里是映射文件路径的方法
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String os = System.getProperty("os.name");

        if (os.toLowerCase().startsWith("win")) {  //如果是Windows系统
            registry.addResourceHandler("/img/**")
                    // /apple/**表示在磁盘apple目录下的所有资源会被解析为以下的路径
                    .addResourceLocations("file:" + CommTool.imgUrl + "/") //媒体资源
                    .addResourceLocations("classpath:/META-INF/resources/");  //swagger2页面
        } else {  //linux 和mac
            registry.addResourceHandler("/smallapple/**")
                    .addResourceLocations("file:/resources/smallapple/")   //媒体资源
                    .addResourceLocations("classpath:/META-INF/resources/");  //swagger2页面;
        }
    }

}
