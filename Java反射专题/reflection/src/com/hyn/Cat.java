package com.hyn;

import javax.jws.soap.SOAPBinding;

/**
 * @Auther: hyn
 * @Date: 2022/6/2 - 06 - 15:18
 * @Description: com.hyn
 * @Version: 1.0
 */
public class Cat {
    public String name = "招财猫";
    public Cat(){}

    public Cat(String name) {
        this.name = name;
    }

    public void hi(){
//        System.out.println("hi"+name);
    }
    public  void cry(){
        System.out.println("招财猫，喵喵叫");
    }
}
