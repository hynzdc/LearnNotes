package com.hyn.outputstream;

import java.io.*;

public class BufferedCopy {
    public static void main(String[] args) throws Exception {
        String srcPath = "/Users/austin/Documents/IdeaProject/io-stream/lalal.mp4";
        String tarPath = "/Users/austin/Documents/IdeaProject/io-stream/test/dudu.mp4";
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(srcPath));
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(tarPath));
        byte[] bytes = new byte[1024];
        int readLen = 0;
        while ((readLen = bufferedInputStream.read(bytes)) != -1) {
            bufferedOutputStream.write(bytes, 0, readLen);
        }
        System.out.println("复制完成");
        if (bufferedInputStream != null) {
            bufferedInputStream.close();
        }
        if (bufferedOutputStream != null) {
            bufferedOutputStream.close();
        }

    }
}
