package com.hyn.transformation;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

/**
 * 将字节流转换成字符流
 * FileInputStream -> InputStreamReader
 */
public class InputStreamReader_ {
    public static void main(String[] args) throws Exception {
        String filePath = "/Users/austin/Documents/IdeaProject/io-stream/test/love.txt";
        //1.把FileInputStream转为inputStreamReader
        //2.指定编码gbk
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(filePath),"UTF-16");
        //3.把inputStreamReader传入BufferedReader
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        //4.读取
        String s = bufferedReader.readLine();
        System.out.println(s);
        //关闭外层流
        inputStreamReader.close();
    }
}
