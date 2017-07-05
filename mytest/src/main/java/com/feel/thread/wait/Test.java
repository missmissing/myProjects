package com.feel.thread.wait;

/**
 * Created by yulong.li on 2017/6/22.
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {

        String lock = new String("");
        Add add = new Add(lock);
        Subtract sub = new Subtract(lock);

        ThreadAdd addthread = new ThreadAdd(add);

        ThreadSubtract sub1 = new ThreadSubtract(sub);
        sub1.start();

        ThreadSubtract sub2 = new ThreadSubtract(sub);
        sub2.start();

        Thread.sleep(1000);
        addthread.start();

    }
}
