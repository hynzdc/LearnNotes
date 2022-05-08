package com.hyn.redis.dao;

import com.hyn.redis.entity.User;
import com.hyn.redis.entity.vo.UserVo;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface UserDao {
    List<User> findAll();

    User findById(String id);

    void delete(String id);

    void save(User user);

    void update(User user);

    List<UserVo> getUserAndEmp();
}
