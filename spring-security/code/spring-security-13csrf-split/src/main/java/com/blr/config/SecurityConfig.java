package com.blr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(User.withUsername("root").password("{noop}123").roles("admin").build());
        return inMemoryUserDetailsManager;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //自定义 filter 交给工厂管理
    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setFilterProcessesUrl("/doLogin");//指定认证 url
        loginFilter.setUsernameParameter("uname");//指定接收json 用户名 key
        loginFilter.setPasswordParameter("passwd");//指定接收 json 密码 key
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        //认证成功处理
        loginFilter.setAuthenticationSuccessHandler((req, resp, authentication) -> {
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("msg", "登录成功");
            result.put("用户信息", authentication.getPrincipal());
            resp.setContentType("application/json;charset=UTF-8");
            resp.setStatus(HttpStatus.OK.value());
            String s = new ObjectMapper().writeValueAsString(result);
            resp.getWriter().println(s);
        });
        //认证失败处理
        loginFilter.setAuthenticationFailureHandler((req, resp, ex) -> {
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("msg", "登录失败: " + ex.getMessage());
            resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            resp.setContentType("application/json;charset=UTF-8");
            String s = new ObjectMapper().writeValueAsString(result);
            resp.getWriter().println(s);
        });
        return loginFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
                .and()
                .formLogin()
                //.and().csrf().disable();
                .and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());//将令牌保存到 cookie 中 允许 cookie 前端获取
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
