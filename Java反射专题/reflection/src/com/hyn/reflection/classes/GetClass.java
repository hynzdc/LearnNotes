package com.hyn.reflection.classes;

import com.hyn.Car;

/**
 * @Auther: hyn
 * @Date: 2022/6/3 - 06 - 15:46
 * @Description: com.hyn.reflection.classes
 * @Version: 1.0
 * 演示得到class对象的各种方式
 */
public class GetClass {
    public static void main(String[] args) throws ClassNotFoundException {
        //1.如果已经知道一个类的全类名，且该类在类路径下，可以通过Class的静态方法
        //forName获取，多用于配置文件
        String classFullName = "com.hyn.Car";
        Class<?> cls = Class.forName(classFullName);
        System.out.println(cls);
        //2.类名.class 应用场景参数传递
        Class<Car> cls2 = Car.class;
        System.out.println(cls2);
        //3.如果对象的实例已经存在，则通过getClass可以得到他的Class
        Car car = new Car();
        Class cls3  = car.getClass();
        System.out.println(cls3);
        //4.通过类加载器来获取到类的Class对象，类加载区有四种
        //（1）先得到类加载起
        ClassLoader classLoader = car.getClass().getClassLoader();
        //(2) 通过类加载器得到类加载对象
        Class<?> cls4 = classLoader.loadClass(classFullName);
        System.out.println(cls4);
        //cls1,cls2,cls3,cl4都是同一个class，因为只加载一次
        //5.基本数据类型(int,char,boolean,float,double,byte,long,short)按
        //如下的方式得到Class对象
        Class<Integer> integerClass = int.class;
        System.out.println(integerClass);
        Class<Boolean> booleanClass = boolean.class;
        System.out.println(booleanClass);
        //6.基本数据类型的包装类可以通过.type来得到Class对象
        Class<Integer> type = Integer.TYPE;
        System.out.println(type);
        Class<Character> type1 = Character.TYPE;
        System.out.println(type1);
        System.out.println(integerClass.hashCode()==type.hashCode());
    }
}
