package com.hyn.properties;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Properties01_ {
    public static void main(String[] args) throws Exception {
        //用传统的方法读取mysql.properties
        BufferedReader bufferedReader = new BufferedReader(new FileReader("/Users/austin/Documents/IdeaProject/io-stream/src/mysql.properties"));
        String line = "";
        while ((line = bufferedReader.readLine())!=null){
            String[] split = line.split("=");
            System.out.println(split[0]+"值是"+split[1]);
        }
        //这种方式也可以但是如果我要只获取到ip就比较麻烦了
        bufferedReader.close();
    }
}
