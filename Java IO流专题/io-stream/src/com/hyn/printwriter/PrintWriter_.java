package com.hyn.printwriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class PrintWriter_ {
    public static void main(String[] args) throws IOException {
        //PrintWriter printWriter = new PrintWriter(System.out);
        PrintWriter printWriter = new PrintWriter(new FileWriter("/Users/austin/Documents/IdeaProject/io-stream/test/hyn.txt"));
        printWriter.println("北京你好");
        printWriter.close();
    }
}
