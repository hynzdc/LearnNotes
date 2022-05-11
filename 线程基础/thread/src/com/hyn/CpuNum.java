package com.hyn;

public class CpuNum {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        //可获得的进程，获取当前电脑的cpu数量
        int cpuNum = runtime.availableProcessors();
        System.out.println(cpuNum);
    }
}
