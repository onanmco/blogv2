package com.cemonan.blog.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class HandlerInterceptorAppConfig implements WebMvcConfigurer {

    private final RequiresAuthInterceptor requiresAuthInterceptor;

    @Autowired
    public HandlerInterceptorAppConfig(RequiresAuthInterceptor requiresAuthInterceptor) {
        this.requiresAuthInterceptor = requiresAuthInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requiresAuthInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login/**")
                .excludePathPatterns("/register/**");
    }
}
