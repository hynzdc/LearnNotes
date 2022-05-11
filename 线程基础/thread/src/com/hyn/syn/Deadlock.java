package com.hyn.syn;

public class Deadlock {
    public static void main(String[] args) {
        //模拟一个死锁现象
        DeadlockDemo A = new DeadlockDemo(true);
        DeadlockDemo B = new DeadlockDemo(false);
        A.setName("A线程");
        B.setName("B线程");
        A.start();
        B.start();
    }
}

class DeadlockDemo extends Thread {
    //保证多线程共用一个对象，这里使用static
    static Object o1 = new Object();
    static Object o2 = new Object();
    boolean flag;

    public DeadlockDemo(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        //1.如果flag为T，线程A就会先得到o1对象锁，然后尝试获取o2对象锁
        //2.如果线程A得不到o2对象锁就会blocked
        //3.如果flag为F，线程B就会先得到o2对象锁，然后尝试获取o1对象锁
        //4.如果线程B得到不o1对象锁就会blocked
        if (flag) {
            synchronized (o1){//对象互斥锁锁，同步下面代码
                System.out.println(Thread.currentThread().getName()+"进入1");
                synchronized (o2){//这里获取li对象的监视权
                    System.out.println(Thread.currentThread().getName()+"进入2");
                }
            }
        }else {
            synchronized (o2){//
                System.out.println(Thread.currentThread().getName()+"进入3");
                synchronized (o1){
                    System.out.println(Thread.currentThread().getName()+"进入4");
                }
            }
        }
    }
}
