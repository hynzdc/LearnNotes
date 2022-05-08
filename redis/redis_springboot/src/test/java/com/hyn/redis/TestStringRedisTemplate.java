package com.hyn.redis;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

//这两句话相当于启动springboot应用
@SpringBootTest(classes = RedisSpringbootApplication.class)
@RunWith(SpringRunner.class)
public class TestStringRedisTemplate {
    //注入stringredistemplate
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //操作key相关
    @Test
    public void testKey(){
        System.out.println(stringRedisTemplate.keys("*"));
        Boolean name = stringRedisTemplate.hasKey("name");
        stringRedisTemplate.type("name");
        //stringRedisTemplate.expire("name",1, TimeUnit.HOURS);
        System.out.println(stringRedisTemplate.getExpire("name"));
        stringRedisTemplate.randomKey();
        //stringRedisTemplate.rename("name","name1");
        stringRedisTemplate.move("name1",1);
    }
    @Test
    public void testString(){
        stringRedisTemplate.opsForValue().set("name","xiaoning");
        System.out.println(stringRedisTemplate.opsForValue().get("name"));
        stringRedisTemplate.opsForValue().set("code","2357",120,TimeUnit.SECONDS);
    }
    @Test
    public void testList(){
        stringRedisTemplate.opsForList().leftPush("names","sjj");
        List<String> list = new ArrayList<>();
        list.add("小红");
        stringRedisTemplate.opsForList().leftPushAll("names",list);
        stringRedisTemplate.opsForList().range("names",0,-1);
        stringRedisTemplate.opsForList().trim("names",0,1);
        stringRedisTemplate.opsForList().remove("names",2,"小红");
    }
    @Test
    public void testSet(){
        stringRedisTemplate.opsForSet().add("set","hahah","gagagga");
        stringRedisTemplate.opsForSet().members("set");
        //stringRedisTemplate.opsForSet().scan("set",
    }
    public void testZset(){
        stringRedisTemplate.opsForZSet().add("zset","huahua",10);
        Set<String> zset = stringRedisTemplate.opsForZSet().range("zset", 0, -1);
        zset.forEach(v-> System.out.println(v));
        Set<ZSetOperations.TypedTuple<String>> zset1 = stringRedisTemplate.opsForZSet().rangeByScoreWithScores("zset", 0, 100);
        zset1.forEach(z-> System.out.println(z));
    }
    @Test
    public void testObject(){

    }

}
