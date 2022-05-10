package com.hyn.file;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class FileCreate {
    public static void main(String[] args) {

    }
    @Test
    //方式一
    public void create01(){
        String filePath = "/Users/austin/Documents/IdeaProject/io-stream/1.txt";
        File file = new File(filePath);
        try {
            file.createNewFile();
            System.out.println("文件创建成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    //方式二 new File(File parent,String child) 根据父目录文件+子路径构建
    public void create02(){
        File parentFile = new File("/Users/austin/Documents/IdeaProject/io-stream");
        String fileName = "2.txt";
        //这个动作相当于只在内存里有一个对象，还没有和硬盘发生任何关系
        File file = new File(parentFile, fileName);
        try {
            //只有执行了这个方法才会创建
            file.createNewFile();
            System.out.println("创建成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    //方式三 new File(String parentPath,String child)
    public void create() {
        String parentPath = "/Users/austin/Documents/IdeaProject/io-stream/";
        String fileName = "3.txt";
        File file = new File(parentPath, fileName);
        try {
            file.createNewFile();
            System.out.println("创建成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
