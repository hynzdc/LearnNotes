package com.hyn.method;

public class ThreadMethod02 {
    public static void main(String[] args) throws InterruptedException {
        T2 t2 = new T2();
        t2.start();
        for (int i = 0;i<20;i++){
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName()+"（小弟）主线程吃包子"+i);
            if (i==4){
                System.out.println("小弟让老大先都吃完");
                //t2.join();
                Thread.yield();//礼让不一定成功
            }
        }
    }
}
class T2 extends Thread{
    @Override
    public void run() {
        for (int i=0;i<=20;i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"（老大）子线程在吃包子"+i);
        }
    }
}
