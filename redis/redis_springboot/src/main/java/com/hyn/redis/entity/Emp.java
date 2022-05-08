package com.hyn.redis.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class Emp implements Serializable {
    private String id;
    private String name;
}
