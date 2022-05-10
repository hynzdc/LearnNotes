package com.hyn.properties;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Properties;

public class Properties02_ {
    public static void main(String[] args) throws Exception {
        //使用Properties这个类来读取mysql.properties
        //1,创建对象
        Properties properties = new Properties();
        //2,加载指定配置文件
        properties.load(new FileReader("/Users/austin/Documents/IdeaProject/io-stream/src/mysql.properties"));
        //3,把k-v显示控制台
        properties.list(System.out);
        //4,根据key获取对应的值
        System.out.println(properties.get("user"));
        System.out.println(properties.get("pwd"));
    }
}
