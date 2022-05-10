package com.hyn.outputstream;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOutputStream_ {
    public static void main(String[] args) {

    }
    @Test
    //将数据写入文件中，如果文件不存在则创建文件
    public void writeFile(){
        FileOutputStream fileOutputStream = null;
        String filePath = "/Users/austin/Documents/IdeaProject/io-stream/hello.txt";

        try {
            //1.new FileOutputStream(filePath)写入内容会覆盖原来的内容
            //2.new FileOutputStream(filePath,true) 写入内容会追加在末尾
            //fileOutputStream = new FileOutputStream(filePath);
            fileOutputStream = new FileOutputStream(filePath,true);
            //写入一个字节
            //''代码的是char  "" 代表的是string
            fileOutputStream.write('a');//char会自动转换成int
            //写一个字符串
            String str = "hello hyn";
            byte[] bytes = str.getBytes();
            fileOutputStream.write(bytes);

            fileOutputStream.write(bytes,0,3);//从第0开始写3个进去
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
