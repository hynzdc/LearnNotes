package com.hyn.redis.service;

import com.hyn.redis.dao.EmpDao;
import com.hyn.redis.entity.Emp;
import com.hyn.redis.entity.vo.UserEmp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmpServiceImpl implements EmpService{
    @Autowired
    private EmpDao empDao;
    @Override
    public List<Emp> findAll() {
        return empDao.findAll();
    }

    @Override
    public UserEmp findUserEmp(String id) {
        return empDao.findUserEmp(id);
    }
}
