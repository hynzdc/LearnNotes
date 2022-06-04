package com.hyn.reflection.classload;

/**
 * @Auther: hyn
 * @Date: 2022/6/3 - 06 - 19:50
 * @Description: com.hyn.reflection.classload
 * @Version: 1.0
 * 类加载-初始化阶段
 */
public class ClassLoad03 {
    public static void main(String[] args) {
        //1.加载B类，并生成B类的Class对象
        //2.连接 number = 0
        //3.初始化阶段，一次收集静态变量和静态代码块中的语句
        /*
        System.out.println("B的静态代码被执行");
        number = 300;
        number = 100;
         System.out.println("B的构造器被执行");
         number = 100 了
         */

       // B b = new B();
        System.out.println(B.number);//这个语句并不会执行构造器
        new B();//这里会多输出构造器这个
    }
}

class B {
    static {
        System.out.println("B的静态代码被执行");
        number = 300;
    }

    static int number = 100;

    public B() {
        System.out.println("B的构造器被执行");
    }
}
