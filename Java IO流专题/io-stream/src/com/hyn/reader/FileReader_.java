package com.hyn.reader;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileReader_ {
    public static void main(String[] args) {

    }


    @Test
    //一个一个读取
    public void reader(){
        String filePath = "/Users/austin/Documents/IdeaProject/io-stream/love.txt";
        FileReader fileReader = null;
        int data = 0;
        try {
            fileReader = new FileReader(filePath);
            //循环读
            while ((data = fileReader.read())!=-1){
                System.out.print((char)data);
            }
            System.out.println("读取完毕");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Test
    //使用字符数组读取文件
    public void reader2(){
        String filePath = "/Users/austin/Documents/IdeaProject/io-stream/love.txt";
        int readLen = 0;
        FileReader fileReader = null;
        char[] bufs = new char[10];
        try {
            fileReader = new FileReader(filePath);
            while ((readLen = fileReader.read(bufs))!=-1){
                System.out.print(new String(bufs,0,readLen));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
