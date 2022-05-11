package com.hyn.ticket;

public class SellTickets {
    //这种方式出现了超卖现象
    public static void main(String[] args) {
        SellTicket sellTicket01 = new SellTicket();
        SellTicket sellTicket02 = new SellTicket();
        SellTicket sellTicket03 = new SellTicket();
        sellTicket01.start();
        sellTicket02.start();
        sellTicket03.start();
    }
}
//使用Thread方式
class SellTicket extends Thread{
    private static int ticketNum = 100;
    private static boolean loop = true;
    public static void sell(){
        synchronized (SellTicket.class){
            if (ticketNum<=0){
                System.out.println("售票结束");
                loop = false;
                return;
            }
            //休眠50毫秒
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("窗口"+Thread.currentThread().getName()+"售出一张票"+"剩余票数"+(--ticketNum));

        }
    }
    @Override
    public void run() {
       while (loop){
            sell();
       }
    }
}
