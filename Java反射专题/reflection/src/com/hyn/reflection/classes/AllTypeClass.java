package com.hyn.reflection.classes;

import java.io.Serializable;

/**
 * @Auther: hyn
 * @Date: 2022/6/3 - 06 - 16:11
 * @Description: com.hyn.reflection.classes
 * @Version: 1.0
 * 哪些类型有Class对象
 */
public class AllTypeClass {
    public static void main(String[] args) {
        Class<String> stringClass = String.class;
        Class<Serializable> serializableClass = Serializable.class;
        Class<Integer[]> aClass = Integer[].class;
        Class<float[][]> aClass1 = float[][].class;
        Class<Deprecated> deprecatedClass = Deprecated.class;
    }
}
