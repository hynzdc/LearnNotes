package com.hyn.reflection.classload;

/**
 * @Auther: hyn
 * @Date: 2022/6/3 - 06 - 19:33
 * @Description: com.hyn.reflection.classload
 * @Version: 1.0
 * 说明一下连接阶段的准备
 */
public class ClassLoad02 {
    public static void main(String[] args) {

    }
}
class A{
    //属性-成员变量-字段
    //分析连接阶段准备是如何处理的？
    //n1是实例变量，在准备阶段是不会分配内存的
    //n2是静态变量会分配内存，默认值是0，而不是20
    //n3其实是个常量，和静态变量不一样，它会一次性非配，一旦赋值就不变了
    public int n1 = 10;
    public static  int n2 = 20;
    public static final int n3 = 30;
}
