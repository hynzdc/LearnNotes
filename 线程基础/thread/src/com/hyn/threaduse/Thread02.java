package com.hyn.threaduse;

public class Thread02 {
    public static void main(String[] args) {
        Dog dog = new Dog();
        //dog.start这里不能调用start
        Thread thread = new Thread(dog);
        thread.start();
    }
}
class Dog implements Runnable{//通过实现Runnable接口
    int count = 0;
    @Override
    public void run() {
        while (true){

            System.out.println("小狗汪汪叫"+(++count));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (count==10){

            }
        }
    }
}
