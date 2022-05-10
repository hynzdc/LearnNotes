package com.hyn.standard;

public class InputAndOutput {
    public static void main(String[] args) {
        //System.in 编译类型是  inputStream
        //运行类型是  BufferedInputStream
        System.out.println(System.in.getClass());

        //public final static PrintStream out = null;
        //编译类型 PrintStream
        //运行类型 PrintStream
        System.out.println(System.out.getClass());
    }
}
