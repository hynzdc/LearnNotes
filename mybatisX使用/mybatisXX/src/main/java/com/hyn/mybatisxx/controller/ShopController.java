package com.hyn.mybatisxx.controller;

import com.hyn.mybatisxx.domain.Shop;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.hyn.mybatisxx.service.ShopService;

import java.util.List;

@RestController
@Api(tags = "用户服务的相关接口")
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    private ShopService service;
    @PostMapping("/add")
    @ApiOperation(value = "增加",notes = "哈哈")
    public void addShop(@RequestBody Shop shop){
        service.save(shop);
    }
    @GetMapping("findAll")
    public List<Shop> findAll(){
        return service.list();
    }
}
