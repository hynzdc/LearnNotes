package com.hyn.outputstream;

import java.io.Serializable;

public class Dog implements Serializable {
    //serialVersionUID序列化版本，提高兼容性
    private static final long serialVersionUID = 1L;
    private String name;
    private int age;

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Dog(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
