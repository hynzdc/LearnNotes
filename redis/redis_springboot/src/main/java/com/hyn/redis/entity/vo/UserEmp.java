package com.hyn.redis.entity.vo;

import com.hyn.redis.entity.Emp;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class UserEmp implements Serializable {
    private String id;
    private String name;
    private List<Emp> emps;
}
