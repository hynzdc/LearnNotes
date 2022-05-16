package com.hyn.jwt.controller;


import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hyn.jwt.entity.Users;
import com.hyn.jwt.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import com.hyn.jwt.utils.jwtUtil;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hyn
 * @since 2021-10-21
 */
@RestController
@RequestMapping("/user")
public class UsersController {
    @Autowired
    private UsersService service;
    @PostMapping("/login")
    public Map<String,Object> login(Users users){
        Map<String,Object> map = new HashMap<>();
        try {
            Users userDB  = service.login(users);
            Map<String,String> payload = new HashMap<>();
            //生成jwt令牌
            payload.put("id",userDB.getId().toString());
            payload.put("username",userDB.getUsername());
            String token = jwtUtil.getToken(payload);
            map.put("state",200);
            map.put("msg","登录成功");
            map.put("token",token);//响应token
        }catch (Exception e){
            map.put("state",408);
            map.put("msg",e.getMessage());
        }
        return map;
    }
    @PostMapping("/test")
    public Map<String,Object> test(){
        Map<String,Object> map = new HashMap<>();
        map.put("state",true);
        map.put("msg","请求成功");
        return map;
    }
}

