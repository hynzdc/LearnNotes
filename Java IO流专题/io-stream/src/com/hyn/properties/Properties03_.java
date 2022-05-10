package com.hyn.properties;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Properties03_ {
    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();

        properties.setProperty("name","hyn");
        properties.setProperty("age","23");
        properties.setProperty("hha","郝亚宁");
        properties.store(new FileWriter("/Users/austin/Documents/IdeaProject/io-stream/src/mysql2.properties"),"你是真的帅");
        System.out.println("保存成功");
    }
}
