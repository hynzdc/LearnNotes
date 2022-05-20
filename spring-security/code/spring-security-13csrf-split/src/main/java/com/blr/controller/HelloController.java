package com.blr.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @PostMapping("/hello")
    public String hello() {
        System.out.println("hello");
        return "hello";
    }
}
