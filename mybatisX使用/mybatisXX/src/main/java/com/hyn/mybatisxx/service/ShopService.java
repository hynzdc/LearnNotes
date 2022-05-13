package com.hyn.mybatisxx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hyn.mybatisxx.domain.Shop;

/**
* @author austin
* @description 针对表【shop】的数据库操作Service
* @createDate 2022-05-13 09:30:41
*/
public interface ShopService extends IService<Shop> {
    public void insertShop(Shop shop);
}
