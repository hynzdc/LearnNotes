package com.hyn.reflection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @Auther: hyn
 * @Date: 2022/6/3 - 06 - 09:46
 * @Description: com.hyn.reflection
 * @Version: 1.0
 */
public class Reflection01 {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/re.properties"));
        String classfullpath = properties.get("classfullpath").toString();
        String methodName = properties.get("method").toString();
        //1.加载类,返回Class类型的对象
        Class cls = Class.forName(classfullpath);
        //2.通过cls得到你加载的类com.hyn.Cat的对象实例
        Object o = cls.newInstance();
        System.out.println("o的运行类型" + o.getClass());//得到运行类型
        //3.通过cls得到你加载类的com.hyn.Cat的methodName的方法对象
        //在反射机制中，把方法视为对象，（万物皆对象）
        Method method1 = cls.getMethod(methodName);
        //4.通过method1来调用对象：即通过方法对象来调用方法
        method1.invoke(o);//传统方法 对象.方法(),反射机制 方法.invoke(对象// )
        //得到name字段,getField不能得到私有的属性
        //这个nameField也是个对象
        Field nameField = cls.getField("name");
        System.out.println(nameField.get(o));
        //java.lang.reflect.Constructor代表构造方法
        Constructor constructor = cls.getConstructor();//无参构造器
        System.out.println(constructor);
        //有参数的构造器
        Constructor constructor1 = cls.getConstructor(String.class);//这里传入的是Sring.classes就是String类的class对象
        System.out.println(constructor1);
    }
}
