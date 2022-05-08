package com.hyn.redis.dao;

import com.hyn.redis.entity.Emp;
import com.hyn.redis.entity.vo.UserEmp;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface EmpDao {
    List<Emp> findAll();

    UserEmp findUserEmp(String id);
}
