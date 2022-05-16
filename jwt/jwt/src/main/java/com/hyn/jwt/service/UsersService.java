package com.hyn.jwt.service;

import com.hyn.jwt.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyn
 * @since 2021-10-21
 */
public interface UsersService extends IService<Users> {
    public Users login(Users user);
}
