package com.hyn.jwt.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class jwtUtil {
    private static final String signture = "!&*^hdad&*T*GUGyyugu&*T^T*";
    /**
     * 生成token header.payloder.signture
     */
    public static String getToken(Map<String,String> map){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE ,7);//默认7天过期
        //创建jwtbuilder
        JWTCreator.Builder builder = JWT.create();
        //payload
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });
        String token = builder.withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256(signture));
        return token;
    }
    /**
     * 验证token合法性
     */
    public static DecodedJWT verify(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(signture)).build().verify(token);
        return verify;
    }

    /**
     * 获取token信息的方法
     */
//    public static DecodedJWT getTokenInfo(String token){
//        DecodedJWT verify = JWT.require(Algorithm.HMAC256(signture)).build().verify(token);
//        return verify;
//    }
}
