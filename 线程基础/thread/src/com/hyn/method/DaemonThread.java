package com.hyn.method;

public class DaemonThread {
    public static void main(String[] args) throws InterruptedException {
        MyDaemonThread myDaemonThread = new MyDaemonThread();
        //如果我们希望主线程结束后，子线程可以自动退出
        //只需要将子线程设置为守护线程
        myDaemonThread.setDaemon(true);
        myDaemonThread.start();
        for (int i = 0;i<10;i++){
            //守护宝强哈哈
            System.out.println("宝强在辛苦的拍电影");
            Thread.sleep(1000);
        }
    }
}
class MyDaemonThread extends Thread{
    @Override
    public void run() {
        for (;;){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("狗狗和毛毛在聊天");
        }
    }
}
