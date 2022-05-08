package com.hyn.redis;

import com.hyn.redis.service.EmpService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = RedisSpringbootApplication.class)
@RunWith(SpringRunner.class)
public class TestEmp {
    @Autowired
    private EmpService empService;
    @Test
    public void findAll(){
        System.out.println(empService.findAll());
    }
    @Test
    public void findUserEmp(){
        System.out.println(empService.findUserEmp("1"));
    }
}
