package com.hyn.homework;

public class HomeWork02 {
    public static void main(String[] args) {
        Bank bank = new Bank();
        new Thread(bank).start();
        new Thread(bank).start();
    }
}

class Bank implements Runnable {

    private int deposit = 10000;

    @Override
    public void run() {
        while (true) {
            //当多个线程执行到这里的时候就会争夺this对象锁
            //哪个争夺到了就会执行synchronized代码块
            //争夺不到的就blocked
            synchronized (this) {
                if (deposit < 1000) {
                    System.out.println("余额不足，不可以取");
                    break;
                }
                deposit -= 1000;
                System.out.println(Thread.currentThread().getName() + "取了1000块，剩余" + deposit);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
