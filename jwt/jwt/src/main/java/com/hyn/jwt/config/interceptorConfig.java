package com.hyn.jwt.config;

import com.hyn.jwt.interceptor.jwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class interceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
         registry.addInterceptor(new jwtInterceptor())
                 .addPathPatterns("/user/test")
                 .excludePathPatterns("/user/login");
    }
}
