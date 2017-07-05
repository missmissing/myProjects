package com.feel.thread.waitimpl;

/**
 * Created by yulong.li on 2017/6/22.
 */
public class TestImpl {
    public static void main(String[] args) throws InterruptedException {
        String lock = new String("");


        new Thread(new Subim(lock)).start();

        new Thread(new Subim(lock)).start();

        Thread.sleep(1000);

        new Thread(new Addim(lock)).start();
    }
}
