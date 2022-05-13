package com.hyn.mybatisxx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyn.mybatisxx.domain.Shop;
import com.hyn.mybatisxx.mapper.ShopMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hyn.mybatisxx.service.ShopService;

/**
* @author austin
* @description 针对表【shop】的数据库操作Service实现
* @createDate 2022-05-13 09:30:41
*/
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop>
implements ShopService {
    @Autowired
    private ShopMapper mapper;
    @Override
    public void insertShop(Shop shop) {
        mapper.insertAll(shop);
    }
}
