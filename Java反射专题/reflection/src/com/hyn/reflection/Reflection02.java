package com.hyn.reflection;

import com.hyn.Cat;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Properties;

/**
 * @Auther: hyn
 * @Date: 2022/6/3 - 06 - 10:06
 * @Description: com.hyn.reflection
 * @Version: 1.0
 */
public class Reflection02 {
    public static void main(String[] args) throws Exception {
        m1();
        m2();
    }
    //传统方法
    public static void m1(){
        Cat cat = new Cat();
        long start = System.currentTimeMillis();
        for (int i =0;i<900000000;i++){
            cat.hi();
        }
        long end = System.currentTimeMillis();
        System.out.println("传统方法耗时"+(end-start));
    }
    //反射机制
    public static void m2() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/re.properties"));
        String classfullpath = properties.get("classfullpath").toString();
        String method = properties.get("method").toString();
        Class cls = Class.forName(classfullpath);
        Object o = cls.newInstance();
        Method method1 = cls.getMethod(method);
        method1.setAccessible(true);//取消访问检测
        long start = System.currentTimeMillis();
        for (int i =0;i<900000000;i++){
            method1.invoke(o);
        }
        long end = System.currentTimeMillis();
        System.out.println("反射方法耗时："+(end-start));
    }
}
