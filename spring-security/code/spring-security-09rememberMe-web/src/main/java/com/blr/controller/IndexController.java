package com.blr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class IndexController {


    @GetMapping("/")
    public String index(HttpServletRequest request) {
        System.out.println("index  ok!");
        return "index ok!";
    }
}
