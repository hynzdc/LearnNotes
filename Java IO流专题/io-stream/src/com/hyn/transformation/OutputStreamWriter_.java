package com.hyn.transformation;

import java.io.*;

/**
 * FileOutputStream -> OutputStreamWriter
 */
public class OutputStreamWriter_ {
    public static void main(String[] args) throws Exception {
        String filePath = "/Users/austin/Documents/IdeaProject/io-stream/test/hyn.txt";
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath),"utf-8"));
        bufferedWriter.write("郝亚宁真的帅");
        bufferedWriter.close();
    }
}
