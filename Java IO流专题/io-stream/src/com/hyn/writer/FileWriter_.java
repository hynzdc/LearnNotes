package com.hyn.writer;

import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriter_ {
    //
    @Test
    public void write01(){
        String writePath = "/Users/austin/Documents/IdeaProject/io-stream/test/love.txt";
        //创建fileWriter对象
        int num = '2';
        char[] chars = {'l','o','v','y'};
        System.out.println(num-'0');
        FileWriter fileWriter = null;
        try {
            fileWriter  = new FileWriter(writePath,true);
            //1.写入单个字符
            fileWriter.write('I');
            //2.写入char数组
            fileWriter.write(chars);
            //3.写入指定的数组
            fileWriter.write("郝亚宁真的帅".toCharArray(),0,3);
            //4.写入字符串
            fileWriter.write("你好北京");
            //5.写入字符串的指定部分
            fileWriter.write("你好上海",0,1);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
