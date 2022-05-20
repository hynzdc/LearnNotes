package com.blr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    @Autowired
    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(User.withUsername("root").password("{noop}123").roles("ADMIN").build());
        return inMemoryUserDetailsManager;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //.mvcMatchers("/index").rememberMe()  //指定资源记住我
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .rememberMe() //开启记住我功能
                .tokenRepository(persistentTokenRepository())
                //.rememberMeServices(rememberMeServices()) //指定 rememberService 实现
                //.rememberMeParameter("remember-me") 用来接收请求中哪个参数作为开启记住我的参数
                //.alwaysRemember(true) //总是记住我
                .and()
                .csrf().disable();
    }


    //指定数据库持久化
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        jdbcTokenRepository.setCreateTableOnStartup(false);//启动创建表结构
        return jdbcTokenRepository;
    }

    //指定记住我的实现
//    @Bean
//    public RememberMeServices rememberMeServices() {
//        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
//        tokenRepository.setDataSource(dataSource);
//        //tokenRepository.setCreateTableOnStartup(true);//启动时创建表结构
//        return new PersistentTokenBasedRememberMeServices(UUID.randomUUID().toString(), userDetailsService(), tokenRepository);
//    }

}
