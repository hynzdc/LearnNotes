package com.hyn.transformation;

import java.io.*;

/**
 * 中文乱码问题
 */
public class CodeQuestion {
    public static void main(String[] args) throws IOException {
        //使用bufferedReader读取
        String filePath = "/Users/austin/Documents/IdeaProject/io-stream/test/love.txt";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String s = bufferedReader.readLine();
        System.out.println("读取到的内容:"+s);
        bufferedReader.close();
    }
}
