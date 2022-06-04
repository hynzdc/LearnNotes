package com.hyn.reflection;

import java.lang.reflect.Constructor;

/**
 * @Auther: hyn
 * @Date: 2022/6/4 - 06 - 09:21
 * @Description: com.hyn.reflection
 * @Version: 1.0
 * 通过反射创建实例
 */
public class ReflectionInstanceIsCreated {
    public static void main(String[] args) throws Exception {
        //1 先获取到User类的class对象
        Class<?> cls = Class.forName("com.hyn.reflection.User");
        //2 通过public的无参构造创建实例
        User user = (User)cls.newInstance();
        user.setAge(12);
        user.setName("郝亚宁");
        System.out.println(user);
        //3 通过public的有参构造创建实例
        Constructor<?> constructor = cls.getConstructor(String.class);
        Object hyn = constructor.newInstance("小猪头");
        System.out.println(hyn);
        //4 通过非public的有参构造创建实例
        //（1）先得到私有的构造器对象
        Constructor<?> declaredConstructor = cls.getDeclaredConstructor(int.class, String.class);
        //暴力破解，使用反射可以访问private的构造方法
        declaredConstructor.setAccessible(true);//爆破，使用反射可以访问private构造器
        Object dad = declaredConstructor.newInstance(100, "朱元璋");
        System.out.println(dad);
    }
}
class User{
    private int age;
    private String name;
    public User(){

    }

    public User(String name) {
        this.name = name;
    }

    private User(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
