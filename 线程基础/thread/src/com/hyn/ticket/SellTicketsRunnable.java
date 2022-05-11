package com.hyn.ticket;

import java.lang.reflect.Member;

public class SellTicketsRunnable {
    public static void main(String[] args) {
        //也会出现超卖现象
        System.out.println("使用接口的方式来售票 ");
        SellTickets02 sellTickets02 = new SellTickets02();
        new Thread(sellTickets02).start();//第一个
        new Thread(sellTickets02).start();//第二个
        new Thread(sellTickets02 ).start();//第三个
    }
}

class SellTickets02 implements Runnable {
    private  int ticketNum = 100;//这里用不用静态都可以，因为只需要创建一次这个对象

    @Override
    public void run() {
        while (true) {
            if (ticketNum <= 0) {
                System.out.println("票已经卖完了");
                break;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("窗口"+Thread.currentThread().getName()+"售出一张票"+"剩余票数"+(--ticketNum));
        }
    }
}
