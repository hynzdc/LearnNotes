package com.hyn.file;

import org.junit.Test;

import java.io.File;

public class Directory {
    public static void main(String[] args) {

    }
    @Test
    //判断文件是否存在存在就删除
    public void m1(){
        String filePath = "/Users/austin/Documents/IdeaProject/io-stream/1.txt";
        File file = new File(filePath);
        if (file.exists()){
            file.delete();
            System.out.println("删除成功");
        }else {
            System.out.println("该文件不存在 ");
        }
    }

    @Test
    //判断目录是否存在，存在就删除
    //java目录也被当作文件
    public void m2(){
        String filePath = "/Users/austin/Documents/IdeaProject/io-stream/test";
        File file = new File(filePath);
        if (file.exists()){
            file.delete();
            System.out.println("删除成功");
        }else {
            System.out.println("该目录不存在 ");
        }
    }
    @Test
    //判断目录是否存在，如果存在提示存在，如果不存在就把它创建起来
    public void m3(){
        String directoryPath = "/Users/austin/Documents/IdeaProject/io-stream/test";
        File file = new File(directoryPath);
        if (file.exists()){
            System.out.println("该目录存在");
        }else {
            //mkdirs 创建多级目录
            //mkdir 只创建一级目录
            if (file.mkdir()) {
                System.out.println("创建成功");
            }else {
                System.out.println("创建失败");
            }
        }
    }

}
