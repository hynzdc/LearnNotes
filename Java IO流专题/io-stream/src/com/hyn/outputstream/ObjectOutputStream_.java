package com.hyn.outputstream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ObjectOutputStream_ {
    public static void main(String[] args) throws Exception {
        String filePath = "/Users/austin/Documents/IdeaProject/io-stream/test/data.dat";
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
        //序列化数据
        oos.writeInt(100); //int 会自动装箱 成Integer 而Integer实现了Serializable
        oos.writeBoolean(true); //装箱成Boolean类 也实现了Serializable
        oos.writeChar('a'); //char -> Character 实现了Serializable
        oos.writeDouble(20.34); //double -> Double 实现了Serializable
        oos.writeUTF("郝亚宁"); //String 也实现了序列化接口
        oos.writeObject(new Dog("旺草",22));
        oos.close();
        System.out.println("数据保存完毕");
    }
}
