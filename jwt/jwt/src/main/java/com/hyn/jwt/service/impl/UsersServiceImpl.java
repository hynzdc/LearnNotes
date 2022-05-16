package com.hyn.jwt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyn.jwt.entity.Users;
import com.hyn.jwt.mapper.UsersMapper;
import com.hyn.jwt.service.UsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyn
 * @since 2021-10-21
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {
    @Autowired
    private UsersMapper mapper;
    @Override
    public Users login(Users user) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username",user.getUsername());
        wrapper.eq("password",user.getPassword());
        Users users = mapper.selectOne(wrapper);
        if (users!=null){
            return users;
        }else {
            throw new RuntimeException("登录失败");
        }
    }
}
