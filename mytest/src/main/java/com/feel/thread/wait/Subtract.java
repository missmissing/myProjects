package com.feel.thread.wait;

/**
 * Created by yulong.li on 2017/6/22.
 */
public class Subtract {
    private String lock;

    public Subtract(String lock) {
        super();
        this.lock = lock;
    }

    public void subtract() {
        try {
            synchronized (lock) {
                while (ValueObject.list.size() == 0) {
                    System.out.println("Wait begin ThreadName:" + Thread.currentThread().getName());
                    lock.wait();
                    System.out.println("Wait end ThreadName:" + Thread.currentThread().getName());
                }
                System.out.println("list size " + Thread.currentThread().getName() + ": " + ValueObject.list.size());
                ValueObject.list.remove(0);
                System.out.println("list size " + Thread.currentThread().getName() + ": " + ValueObject.list.size());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
