package com.hyn.exit;

public class ThreadExit_ {
    public static void main(String[] args) throws InterruptedException {
        T t = new T();
        t.start();
        //如果希望可以main线程去控制t，必须可以修改loop，通知方式
        //让主线程休眠10
        Thread.sleep(10*1000);
        t.setLoop(false );
    }
}
class T extends Thread{
    int count = 0;
    //设置一个控制变量
    private boolean loop = true;
    @Override
    public void run() {
        while (loop){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"运行中。。。"+(++count));
        }
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }
}
