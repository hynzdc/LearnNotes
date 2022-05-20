package com.blr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final FindByIndexNameSessionRepository findByIndexNameSessionRepository;

    @Autowired
    public SecurityConfig(FindByIndexNameSessionRepository findByIndexNameSessionRepository) {
        this.findByIndexNameSessionRepository = findByIndexNameSessionRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .logout()
                .and()
                .csrf().disable()
                .sessionManagement()//开启会话管理
                .maximumSessions(1)//允许会话最大并发只能一个客户端
                //.expiredUrl("/login");//传统架构处理方案: 当用户的挤下线之后跳转路径
                .expiredSessionStrategy(event -> { //前后端分离架构处理分案
                    HttpServletResponse response = event.getResponse();
                    Map<String, Object> result = new HashMap<>();
                    result.put("status", 500);
                    result.put("msg", "当前会话已经失效,请重新登录!");
                    String s = new ObjectMapper().writeValueAsString(result);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().println(s);
                    response.flushBuffer();
                })
                .sessionRegistry(sessionRegistry()) //将 session 交给谁管理
                .maxSessionsPreventsLogin(true); //一旦登录 禁止再次登录
    }


    //创建 session 同步到 redis 中方案
    @Bean
    public SpringSessionBackedSessionRegistry sessionRegistry() {
        return new SpringSessionBackedSessionRegistry(findByIndexNameSessionRepository);
    }

    /*@Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }*/
}
