package com.hyn.reflection;

import java.lang.management.BufferPoolMXBean;
import java.lang.reflect.Method;

/**
 * @Auther: hyn
 * @Date: 2022/6/4 - 06 - 10:13
 * @Description: com.hyn.reflection
 * @Version: 1.0
 * 反射操作方法
 */
public class ReflectAccessMethod {
    public static void main(String[] args) throws Exception {
        Class<?> bossClass= Class.forName("com.hyn.reflection.Boss");
        //创建对象
        Boss boss = (Boss)bossClass.newInstance();
        //调用public hi 方法
        //得到hi方法对象
        Method hi = bossClass.getMethod("hi", String.class);
        hi.invoke(boss,"haha");
        //调用 private static方法
        Method hi1 = bossClass.getDeclaredMethod("say", int.class, String.class, char.class);
        //爆破
        hi1.setAccessible(true);
        hi1.invoke(boss,2,"hahah",'d');
        //，因为方法是静态的，还可以这样调用
        hi1.invoke(null,2,"hahah",'d');
        //返回值的问题，在反射中，如果方法有返回值，统一返回Object
        //但是实际运行类型和定义返回类型是一样的
        Object reVal = hi1.invoke(null, 3, "郝亚宁", 'h');
        System.out.println("reVal的运行类型"+reVal.getClass());
    }
}
class Boss{
    public int age;
    private static String name;

    public Boss() {
    }
    private static String say(int n,String s,char c){
        System.out.println(n+" "+s+" "+c+" ");
        return n+" "+s+" "+c+" ";
    }
    public void hi(String s){
        System.out.println("hi"+s);
    }
}
