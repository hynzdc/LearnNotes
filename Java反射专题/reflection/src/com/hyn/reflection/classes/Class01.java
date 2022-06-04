package com.hyn.reflection.classes;

import com.hyn.Cat;

/**
 * @Auther: hyn
 * @Date: 2022/6/3 - 06 - 10:34
 * @Description: com.hyn.reflection.classes
 * @Version: 1.0
 */
public class Class01 {
    public static void main(String[] args) throws ClassNotFoundException {
        //Class是系统创建出来的
       // Cat cat = new Cat();
        //反射方式
//        public Class<?> loadClass(String name) throws ClassNotFoundException {
//            return loadClass(name, false);
//        }
        //仍然是通过ClassLoader加载Cat类
        Class class01 = Class.forName("com.hyn.Cat");
        Class class02 = Class.forName("com.hyn.Cat");
        System.out.println(class01.hashCode());
        System.out.println(class02.hashCode());
        Cat cat = new Cat();
        String string = cat.getClass().toString();
        System.out.println(string);
    }
}
