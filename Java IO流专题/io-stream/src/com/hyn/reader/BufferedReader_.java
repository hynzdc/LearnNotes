package com.hyn.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Spliterator;

public class BufferedReader_ {
    public static void main(String[] args) throws Exception {
        String filePath = "/Users/austin/Documents/IdeaProject/io-stream/love.txt";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        //读取
        //按行读取  当返回为空的话读取完毕
        String line = null;
        while (( line = bufferedReader.readLine())!=null){
            System.out.println(line);
        }

        //关闭流只需要关闭bufferedReader就可以了，底层会关闭节点流
        bufferedReader.close();
    }
}
