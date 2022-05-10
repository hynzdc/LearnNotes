package com.hyn.printstream;

import java.io.IOException;
import java.io.PrintStream;

//演示字节打印流
public class PrintStream_ {
    public static void main(String[] args) throws IOException {
        PrintStream out = System.out;
        //默认情况下PrintStream打印的位置是显示器
        out.print("我爱你");
        //因为print底层是write所以我们可以直接用write答应
        out.write("我真帅".getBytes());
        out.close();
        //我们可以修改打印流的输出位置
        //修改到
        System.setOut(new PrintStream("/Users/austin/Documents/IdeaProject/io-stream/test/hyn.txt"));
        System.out.println("我是靓仔");
    }
}
