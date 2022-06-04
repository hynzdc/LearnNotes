package com.hyn.reflection.question;

import com.hyn.Cat;

import javax.sql.rowset.CachedRowSet;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @Auther: hyn
 * @Date: 2022/6/2 - 06 - 15:22
 * @Description: com.hyn.reflection.question
 * @Version: 1.0
 * 反射问题的引入
 */
@SuppressWarnings({"all"})
public class ReflectionQuestion {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //根据配置文件re.properties来创建Cat对象，并调用方法hi
        //传统的方式
        Cat cat = new Cat();
        cat.hi();

        //尝试做一做明白反射
        //Properties可以都配置文件
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/re.properties"));
        String classfullpath = properties.get("classfullpath").toString();
        String methodName = properties.get("method").toString();
        System.out.println(classfullpath+"=="+methodName);
        //创建对象，传统方法已经不行了
        Cat cat1 = new com.hyn.Cat();//new classfullpath()
        //反射机制解决，反射快速入门
        //1.加载类,返回Class类型的对象
        Class cls = Class.forName(classfullpath);
        //2.通过cls得到你加载的类com.hyn.Cat的对象实例
        Object o = cls.newInstance();
        System.out.println("o的运行类型"+o.getClass());//得到运行类型
        //3.通过cls得到你加载类的com.hyn.Cat的methodName的方法对象
        //在反射机制中，把方法视为对象，（万物皆对象）
        Method method1 = cls.getMethod(methodName);
        //4.通过method1来调用对象：即通过方法对象来调用方法
        method1.invoke(o);//传统方法 对象.方法(),反射机制 方法.invoke(对象// )
    }
}
