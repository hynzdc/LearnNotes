package com.hyn.redis.entity.vo;

import com.hyn.redis.entity.Emp;
import jdk.jfr.DataAmount;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class UserVo implements Serializable {
    private String uid;
    private String uname;
    private Emp emp;
}
