package com.hyn.redis;

import com.hyn.redis.entity.User;
import com.hyn.redis.service.UserService;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

import java.util.Date;

@SpringBootTest(classes = RedisSpringbootApplication.class)
@RunWith(SpringRunner.class)
public class TestUserService {
    @Autowired
    private UserService  userService;
    @Test
    public void testUser(){
        System.out.println(userService.findAll());
        System.out.println("...........................");
        System.out.println(userService.findAll());
    }

    @Test
    public void testFindById(){
       userService.findById("2");
    }
    @Test
    public void testDelete(){
        userService.delete("2");
    }
    @Test
    public void testInsert(){
        User user =  new User();
        user.setName("lbai");
        user.setAge(19);
        user.setBir(new Date());
        userService.save(user);
    }
    @Test
    public void update(){
        User user = new User();
        user.setId("1").setName("hahahha").setAge(22).setBir(new Date());
        userService.update(user);
    }

    @Test
    public void getUserVo(){
        System.out.println(userService.getUserAndEmp());
    }

    @Test
    public void testMd5(){
        String key = "945295390:2821158819:com.hyn.redis.dao.UserDao.getUserAndEmp:0:2147483647:select u.*,e.*\n" +
                "        from user u left join emp e on u.id = e.id:SqlSessionFactoryBean";
        String s = DigestUtils.md5DigestAsHex(key.getBytes());
        System.out.println(s);
    }
}
