package com.hyn.syn;

public class SellTicketsBySyn {
    public static void main(String[] args) {
        sellingTickets sellingTickets = new sellingTickets();
        new Thread(sellingTickets).start();
        new Thread(sellingTickets).start();
        new Thread(sellingTickets).start();
        sellingTickets.m2();
    }
}

class sellingTickets implements Runnable {
    private int ticketNum = 100;
    private boolean loop = true;
    Object object = new Object();
    //对于静态方法锁是加在了sellingTickets.class上了
    public synchronized static void m(){}

    public static void m2(){
        synchronized (Object.class){
            System.out.println("m2");
        }
    }

    public void sell() {
        synchronized (this) {
            if (ticketNum <= 0) {
                System.out.println("票已经卖完了");
                loop = false;
                return;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("窗口" + Thread.currentThread().getName() + "还剩票数：" + (--ticketNum));
        }
    }

    @Override
    public void run() {//同步方法，在同一个时刻只能有一个线程操作这个方法
        while (loop) {
            sell();
        }
    }
}
