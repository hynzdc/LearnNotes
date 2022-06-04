package com.hyn.reflection.classes;

import com.hyn.Car;
import com.hyn.Cat;

import java.lang.reflect.Field;

/**
 * @Auther: hyn
 * @Date: 2022/6/3 - 06 - 11:05
 * @Description: com.hyn.reflection.classes
 * @Version: 1.0
 */
/*
演示Class一些常用方法
 */
public class Class02 {
    public static void main(String[] args) throws Exception {
        String fullPathClass = "com.hyn.Car";
        //获取Car类的class对象
        //<?>代表什么意思？
        //表示不确定的java类型
        Class cls = Class.forName(fullPathClass);
        System.out.println(cls);//显示cls是哪个类的class对象
        System.out.println(cls.getClass());//输出cls的运行类型java.lang.class
        //得到包的名字
        System.out.println(cls.getPackage().getName());
        //得到类的名称
        System.out.println(cls.getName());
        //生成对象实例（把镜子里的car拿出来）
        Car car = (Car)cls.newInstance();
        System.out.println(car);
        //通过反射获得属性 brand  私有属性拿不出来
        Field brand = cls.getField("brand");
        System.out.println(brand.get(car));
        //通过反射给属性赋值
        brand.set(car,"奔驰");
        System.out.println(brand.get(car));
        //希望大家遍历得到所有的属性
        Field[] fields = cls.getFields();
        for (Field field : fields) {
            System.out.println(field.getName());
        }
    }
}
