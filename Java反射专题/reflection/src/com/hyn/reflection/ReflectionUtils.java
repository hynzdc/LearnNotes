package com.hyn.reflection;

import com.sun.tracing.dtrace.ArgsAttributes;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Auther: hyn
 * @Date: 2022/6/4 - 06 - 08:07
 * @Description: com.hyn.reflection
 * @Version: 1.0
 * 演示如何通过反射获取类的结构信息
 */
public class ReflectionUtils {
    public static void main(String[] args) {

    }
    //第一组api
    @Test
    public void api_01() throws Exception {
        //得到Class对象
        Class personClass = Class.forName("com.hyn.reflection.Person");
        //获取全类名
        System.out.println(personClass.getName());
        //获取简单类名
        System.out.println(personClass.getSimpleName());
        //getFields:获取所有public修饰的属性，包含本类以及父类
        Field[] fields = personClass.getFields();
        for (Field field : fields) {
            System.out.println("本类及其父类的属性"+field.getName());
        }
        //getDeclareFields:获取本类中所有的属性
        Field[] declaredFields = personClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println(declaredField.getName());
        }
        //getMethods:获取所有public修饰的方法，包含本类和父类
        Method[] methods = personClass.getMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }
        //getDeclareMethods:获取本类中所有的方法
        System.out.println("//////////////////////////");
        Method[] declaredMethods = personClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            System.out.println(declaredMethod.getName());
        }
        //getConstructors():获取public修饰的修饰器，包含本类以及父类
        Constructor[] constructors = personClass.getConstructors();
        for (Constructor constructor : constructors) {
            System.out.println(constructor.getName());
        }
        //getDeclareConstructors():获取本类所有的构造器
        Constructor[] declaredConstructors = personClass.getDeclaredConstructors();
        for (Constructor declaredConstructor : declaredConstructors) {
            System.out.println("本类所有构造器"+declaredConstructor.getName());
        }
        //getPackages
        System.out.println(personClass.getPackage());
        //superClass
        System.out.println(personClass.getSuperclass());
    }
}
class Father{
    public String hobby;
    public void hi(){

    }
    public Father(){

    }
}
class Person extends Father{
    public String name;
    protected int age;
    String job;
    private double salary;
    //方法

    public Person(){

    }

    public Person(String name) {
        this.name = name;
    }

    public void m1(){

    }
    protected void m2(){

    }
    void m3(){

    }
    private void m4(){

    }

}
