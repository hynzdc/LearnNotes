package com.hyn.redis.service;

import com.hyn.redis.entity.User;
import com.hyn.redis.entity.vo.UserVo;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(String id);

    void delete(String id);

    void save(User user);
    void update(User user);
    List<UserVo> getUserAndEmp();
}
