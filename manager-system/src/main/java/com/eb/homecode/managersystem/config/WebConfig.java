package com.eb.homecode.managersystem.config;

import com.eb.homecode.managersystem.interceptor.AdminValidateInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource(name = "userInfoValidateInterceptor")
    private HandlerInterceptor userInfoValidateInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInfoValidateInterceptor).addPathPatterns("/**");
        registry.addInterceptor(new AdminValidateInterceptor()).addPathPatterns("/admin/**");
    }
}
