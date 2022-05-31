package com.hyn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @Auther: hyn
 * @Date: 2022/5/31 - 05 - 09:57
 * @Description: com.hyn.entity
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "users")
public class User {
    @Id //映射为文档的下划线id
    private Integer id;
    @Field(value = "username")
    private String name;
    private Double salary;
    private Date birthday;
}
