package com.hyn.threaduse;

/**
 * 通过继承Thread实现线程
 */
//1.当一个类继承了Thread类，该类就可以当作线程使用
//2.我们会重写自己的run方法，写上自己的业务代码
//3.run Thread 实现了Runnable接口的run方法
public class Thread01 {
    public static void main(String[] args) throws InterruptedException {
        //创建一个cat对象可以当作线程使用
        cat cat = new cat();
        //启动线程
        cat.start();
        //说明，当main线程启动一个子线程thread-0，主线程不会阻塞，会继续执行
        for(int i=0;i<10;i++){
            System.out.println("主线程"+i);//main
            Thread.sleep(1000);
        }
    }
}

class cat extends Thread{
    int times = 0;
    @Override
    public void run() {
        while (true) {
            System.out.println("喵喵，我是小猫咪"+(++times)+"线程名："+Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (times == 8){
                break;//退出循环
            }
        }

    }
}

