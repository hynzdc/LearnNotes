package com.hyn.redis;

import com.hyn.redis.entity.User;
import org.apache.tomcat.util.buf.UEncoder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest(classes = RedisSpringbootApplication.class)
@RunWith(SpringRunner.class)
public class TestRedisTemplate {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void testRedisTemplate(){

        User user = new User();
        //修改key的序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //修改hash key的序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        user.setId("1").setName("hyn").setAge(12).setBir(new Date());
        redisTemplate.opsForValue().set("user",user);
        User user1 = (User) redisTemplate.opsForValue().get("user");//
        System.out.println(user1);
        redisTemplate.opsForList().leftPush("user1",user);
        redisTemplate.opsForSet().add("userSet",user);
        redisTemplate.opsForZSet().add("zsetUser",user,10);
        redisTemplate.opsForHash().put("map","user",user);
    }
}
