package com.feel.thread.wait;

/**
 * Created by yulong.li on 2017/6/22.
 */
public class Add {
    private String lock;

    public Add(String lock) {
        super();
        this.lock = lock;
    }

    public void add() {
        synchronized (lock) {
            ValueObject.list.add("anything");
            lock.notifyAll();
        }
    }
}
