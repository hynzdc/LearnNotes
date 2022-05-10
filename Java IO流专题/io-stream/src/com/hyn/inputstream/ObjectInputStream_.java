package com.hyn.inputstream;


import com.hyn.outputstream.Dog;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * 反序列化
 */
public class ObjectInputStream_ {
    public static void main(String[] args) throws Exception  {
        String filePath = "/Users/austin/Documents/IdeaProject/io-stream/test/data.dat";
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));
        //读取的顺序一定要和存放（序列化）的顺序的一致
        System.out.println(ois.readInt());
        System.out.println(ois.readBoolean());
        System.out.println(ois.readChar());
        System.out.println(ois.readDouble());
        System.out.println(ois.readUTF());
        Object dog = ois.readObject();
        System.out.println("dog类型"+dog.getClass());
        //特别重要的细节
        //如果我们需要用到Dog的方法，需要向下转型
        //需要我们要将Dog类定义，
        Dog dog1 = (Dog)dog;
        System.out.println(dog1.getName());
        System.out.println(dog);//底层object ->dog
        ois.close();
    }
}

