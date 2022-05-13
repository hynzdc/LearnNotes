package com.hyn.mybatisxx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.hyn.mybatisxx.mapper")
public class MybatisXxApplication {
    public static void main(String[] args) {
        SpringApplication.run(MybatisXxApplication.class, args);
    }

}
