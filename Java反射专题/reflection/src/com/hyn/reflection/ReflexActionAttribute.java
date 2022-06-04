package com.hyn.reflection;

import java.lang.reflect.Field;

/**
 * @Auther: hyn
 * @Date: 2022/6/4 - 06 - 09:52
 * @Description: com.hyn.reflection
 * @Version: 1.0
 * 反射操作属性
 */
public class ReflexActionAttribute {
    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("com.hyn.reflection.Student");
        //2创建对象
        Student student = (Student)cls.newInstance();
        //3使用反射得到属性
        Field age = cls.getField("age");
        age.set(student,12);//通过反射来操作属性
        System.out.println(student);
        System.out.println(age.get(student));
        //4 使用反射操作name 私有静态
        Field name = cls.getDeclaredField("name");
        name.setAccessible(true);
        name.set(null,"郝亚宁");//student写成null也可以，因为student是静态的属于所有的
        System.out.println(student);
    }

}
class Student{
    public int age;
    private static String name;

    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +","+
                "name=" + name +
                '}';
    }
}
