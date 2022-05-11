package com.hyn.method;

public class ThreadMethod {
    public static void main(String[] args) throws InterruptedException {
        T t = new T();
        t.setPriority(Thread.MIN_PRIORITY);
        t.start();
        System.out.println(t.getName());
        for (int i=0;i<10;i++){
            Thread.sleep(1000);
            System.out.println("hi");
        }
        System.out.println(t.getName()+"线程的有限级"+t.getPriority());
        t.interrupt();//直接把把休眠中断了
    }
}
class T extends Thread{

    @Override
    public void run() {
        while (true){
            for (int i = 0;i<10;i++){
                System.out.println("吃包子");
            }
            try {
                System.out.println(Thread.currentThread().getName()+"休眠中");
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName()+"被interrupt掉了");
                e.printStackTrace();
            }
        }
    }
}
