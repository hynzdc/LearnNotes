package com.hyn.outputstream;

import org.junit.Test;

import java.io.*;

public class FileCopy_ {
    public static void main(String[] args) {

    }

    @Test
    //完成文件拷贝
    //1.创建文件的输入流，将文件读入到程序
    //2.创建文件输出流，将文件写入到指定的文件
    public void copyFile() {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        String inputPath = "/Users/austin/Documents/IdeaProject/io-stream/hello.txt";
        String outputPath = "/Users/austin/Documents/IdeaProject/io-stream/test/hello.txt";
        byte[] buf = new byte[1024];
        int readLen = 0;
        try {
            fileInputStream = new FileInputStream(inputPath);
            fileOutputStream = new FileOutputStream(outputPath);
            while ((readLen = fileInputStream.read(buf)) != -1) {
                //读了就写，一边读一边写
                fileOutputStream.write(buf, 0, readLen);//一定要使用这个方法
            }
            System.out.println("拷贝成功");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
