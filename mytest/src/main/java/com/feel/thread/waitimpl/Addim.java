package com.feel.thread.waitimpl;

import com.feel.thread.wait.*;

/**
 * Created by yulong.li on 2017/6/22.
 */
public class Addim implements Runnable{
    private String lock;

    public Addim(String lock) {
        super();
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock) {
            ValueObjectImpl.list.add("anything");
            lock.notifyAll();
        }
    }
}
