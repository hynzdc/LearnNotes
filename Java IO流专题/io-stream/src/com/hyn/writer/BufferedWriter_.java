package com.hyn.writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BufferedWriter_ {
    public static void main(String[] args) throws Exception {
        String filePath = "/Users/austin/Documents/IdeaProject/io-stream/test/fuck.txt";
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath,true));
        bufferedWriter.write("我爱你");
        bufferedWriter.newLine();
        bufferedWriter.write("你看起来真的很厉害");
        bufferedWriter.close();
    }
}
