package com.feel.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lyan on 2017/3/14.
 */
public class ThreadTest {
    private AtomicInteger count = new AtomicInteger(0);
    private int isRun;

    public static void main(String[] args) {
        ThreadTest threadTest = new ThreadTest();
        Thread t1 = new Thread(threadTest.new MyThread(true));
        Thread t2 = new Thread(threadTest.new MyThread(true));
        Thread t3 = new Thread(threadTest.new MyThread(false));
        Thread t4 = new Thread(threadTest.new MyThread(false));
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }

    class MyThread implements Runnable{
        private boolean isPlus;
        public MyThread(boolean isPlus){
            this.isPlus = isPlus;
        }

        public void run() {
            while (isRun ++ < 100){
                if(isPlus){
                    count.incrementAndGet();
                }else{
                    count.decrementAndGet();
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName()+":"+count);
            }
        }
    }
}
