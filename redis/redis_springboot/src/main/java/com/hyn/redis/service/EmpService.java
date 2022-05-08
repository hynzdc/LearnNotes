package com.hyn.redis.service;

import com.hyn.redis.entity.Emp;
import com.hyn.redis.entity.vo.UserEmp;

import java.util.List;

public interface EmpService {
   List<Emp> findAll();

   UserEmp findUserEmp(String id);
}
