package com.hyn.threaduse;


//静态代理模式实现简易的Runnable
public class Thread03 {
    public static void main(String[] args) {
        tiger tiger = new tiger();
        ThreadProxy threadProxy = new ThreadProxy(tiger);
        threadProxy.start();
    }
}


class animal{

}
class tiger extends animal implements Runnable{


    @Override
    public void run() {
        System.out.println("老虎嗷嗷叫");
    }
}



//一个Thread代理
class ThreadProxy implements Runnable{

    private Runnable target;

    @Override
    public void run() {
        if (target!=null){
            target.run();
        }
    }

    public ThreadProxy(Runnable target) {
        this.target = target;
    }
    public void start(){
        start0();
    }
    public void start0(){
        run();
    }
}
