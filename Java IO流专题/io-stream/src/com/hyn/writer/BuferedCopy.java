package com.hyn.writer;

import java.io.*;

public class BuferedCopy {
    public static void main(String[] args) throws Exception {
        //BufferedReader 和BufferedWriter 是操作字符的
        //不要操作二进制文件，不如视频，音频
        String  srcFile = "/Users/austin/Documents/IdeaProject/io-stream/2.txt";
        String  tarFile = "/Users/austin/Documents/IdeaProject/io-stream/test/2.txt";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(srcFile));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tarFile));
        String line;
        while ((line = bufferedReader.readLine())!=null){
            //没读取一行就写入
            bufferedWriter.write(line);
            //插入一个换行
            bufferedWriter.newLine();
        }
        if (bufferedReader!=null){
            bufferedReader.close();
        }
        if (bufferedWriter!=null){
            bufferedWriter.close();
        }


    }
}
