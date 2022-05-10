package com.hyn.inputstream;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileInputStream_ {
    public static void main(String[] args) {

    }

    //单个字节读取效率比较低
    @Test
    public void readFile01() {
        FileInputStream fileInputStream = null;
        String filePath = "/Users/austin/Documents/IdeaProject/io-stream/hello.txt";
        int readData = 0;
        try {
            //用于读取文件
            fileInputStream = new FileInputStream(filePath);
            //从该输入流读取一个字节的数据，若没有输入可用，此方法将被阻止
            //如果返回-1，读取完毕
            while ((readData = fileInputStream.read()) != -1) {
                System.out.print((char) readData);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //使用read(byte [])读取文件提高效率
    @Test
    public void readFile02() {
        FileInputStream fileInputStream = null;
        String filePath = "/Users/austin/Documents/IdeaProject/io-stream/hello.txt";
        //字节数组 一次读8个字节
        byte[] buf = new byte[8];
        int readLen = 0;
        try {
            //用于读取文件
            fileInputStream = new FileInputStream(filePath);
            //从该输入流最多读取byte.length 字节的数据到字节数组
            //如果返回-1，读取完毕
            //如果读取正常返回实际读取的字节数
            while ((readLen = fileInputStream.read(buf)) != -1) {
                System.out.print(new String(buf, 0, readLen));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
