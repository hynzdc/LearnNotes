package com.hyn.file;

import org.junit.Test;

import java.io.File;
import java.lang.reflect.Field;

public class FileInformation {
    public static void main(String[] args) {

    }
    @Test
    //获取文件的信息
    public void info(){
        File file = new File("/Users/austin/Documents/IdeaProject/io-stream/1.txt");
        //调用相应的方法
        System.out.println("文件名字"+file.getName());
        System.out.println("绝对路径"+file.getAbsolutePath());
        System.out.println("文件父级目录"+file.getParent());
        System.out.println("文件大小"+file.length());
        System.out.println("是否存在？"+file.exists());
        System.out.println("是不是目录？"+file.isDirectory());
        System.out.println("是不是一个文件？"+file.isFile());
    }
}
