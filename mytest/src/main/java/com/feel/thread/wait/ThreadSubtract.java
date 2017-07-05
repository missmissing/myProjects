package com.feel.thread.wait;

/**
 * Created by yulong.li on 2017/6/22.
 */
public class ThreadSubtract extends Thread{
    private Subtract p;

    public ThreadSubtract(Subtract p) {
        this.p = p;
    }


    @Override
    public void run() {
        p.subtract();
    }
}
