package com.baizhi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {


    private final MyUserDetailService myUserDetailService;

    @Autowired
    public WebSecurityConfigurer(MyUserDetailService myUserDetailService) {
        this.myUserDetailService = myUserDetailService;
    }

    //    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
//        userDetailsService.createUser(User.withUsername("aaa").password("{noop}123").roles("admin").build());
//        return userDetailsService;
//    }

    //springboot 对 security 默认配置中  在工厂中默认创建 AuthenticationManager
//    @Autowired
//    public void initialize(AuthenticationManagerBuilder builder) throws Exception {
//        System.out.println("springboot 默认配置: " + builder);
//    }

    //自定义AuthenticationManager  推荐  并没有在工厂中暴露出来
    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        System.out.println("自定义AuthenticationManager: " + builder);
        builder.userDetailsService(myUserDetailService);
    }

    //作用: 用来将自定义AuthenticationManager在工厂中进行暴露,可以在任何位置注入
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/login.html").permitAll()
                .mvcMatchers("/index").permitAll() //放行资源写在任何前面
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html") //用来指定默认登录页面 注意: 一旦自定义登录页面以后必须只能登录url
                .loginProcessingUrl("/doLogin")  //指定处理登录请求 url
                .usernameParameter("uname")
                .passwordParameter("passwd")
                //.successForwardUrl("/index") //认证成功 forward 跳转路径  始终在认证成功之后跳转到指定请求
                //.defaultSuccessUrl("/index", true) //认证成功 redirect 之后跳转   根据上一保存请求进行成功跳转
                .successHandler(new MyAuthenticationSuccessHandler()) //认证成功时处理 前后端分离解决方案
                //.failureForwardUrl("/login.html") //认证失败之后 forward 跳转
                //.failureUrl("/login.html") // 默认 认证失败之后 redirect 跳转
                .failureHandler(new MyAuthenticationFailureHandler()) //用来自定义认证失败之后处理  前后端分离解决方案
                .and()
                .logout()
                //.logoutUrl("/logout")  //指定注销登录 url 默认请求方式必须: GET
                .logoutRequestMatcher(new OrRequestMatcher(
                        new AntPathRequestMatcher("/aa", "GET"),
                        new AntPathRequestMatcher("/bb", "POST")
                ))
                .invalidateHttpSession(true) //默认 会话失效
                .clearAuthentication(true)   //默认 清楚认证标记
                //.logoutSuccessUrl("/login.html") //注销登录 成功之后跳转页面
                .logoutSuccessHandler(new MyLogoutSuccessHandler())  //注销登录成功之后处理
                .and()
                .csrf().disable();//禁止 csrf 跨站请求保护

    }
}
