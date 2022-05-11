package com.hyn.homework;

import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class HomeWork01 {
    public static void main(String[] args) {
        PrintInteger printInteger = new PrintInteger();
        printInteger.start();
        new ReceiveLetter(printInteger).start();
    }
}
class PrintInteger extends Thread{
    private boolean loop = true;


    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (loop){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(random.nextInt(100));
        }
    }
}

class ReceiveLetter extends Thread{
    Scanner scanner = new Scanner(System.in);
    private PrintInteger printInteger;

    public ReceiveLetter(PrintInteger printInteger) {
        this.printInteger = printInteger;
    }

    @Override
    public void run() {
        while (true){
            System.out.println("输入Q表示退出");
            char key = scanner.next().toUpperCase().charAt(0);
            if (key=='Q'){
                printInteger.setLoop(false);
                System.out.println("receive线程退出");
                break;
            }
        }

    }
}
