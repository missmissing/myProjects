package com.feel.thread.wait;

/**
 * Created by yulong.li on 2017/6/22.
 */
public class ThreadAdd extends Thread{
    private Add p;

    public ThreadAdd(Add p) {
        this.p = p;
    }

    @Override
    public void run() {
        p.add();
    }
}
