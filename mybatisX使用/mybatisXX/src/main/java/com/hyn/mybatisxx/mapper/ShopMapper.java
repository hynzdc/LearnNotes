package com.hyn.mybatisxx.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hyn.mybatisxx.domain.Shop;

/**
* @author austin
* @description 针对表【shop】的数据库操作Mapper
* @createDate 2022-05-13 09:30:41
* @Entity .com.hyn.mybatisxx.domain.Shop
*/
@Mapper
public interface ShopMapper extends BaseMapper<Shop> {

    List<Shop> selectAllByName(@Param("name") String name);

    int updateName(@Param("name") String name);

    int insertAll(Shop shop);
}
