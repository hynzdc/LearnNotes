package com.hyn.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = RedisSpringbootApplication.class)
@RunWith(SpringRunner.class)
public class TestBoundAPI {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    //spring data 为了方便我们对redis进行更友好的操作，因此提供了bound api
    @Test
    public void testBound(){
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //redisTemplate stringRedisTemplate 都会涉及对一个key的多次操作
        //我们进行对key的绑定后
        //对字符串的key进行绑定
        BoundValueOperations<String, String> user = stringRedisTemplate.boundValueOps("user");
        user.append("haoren");
        user.get();
    }

}
