package com.feel.thread.waitimpl;

/**
 * Created by yulong.li on 2017/6/22.
 */
public class Subim implements Runnable {
    private String lock;

    public Subim(String lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            synchronized (lock) {
                while (ValueObjectImpl.list.size() == 0) {
                    System.out.println("Wait begin ThreadName:" + Thread.currentThread().getName());
                    lock.wait();
                    System.out.println("Wait end ThreadName:" + Thread.currentThread().getName());
                }
                System.out.println("list size " + Thread.currentThread().getName() + ": " + ValueObjectImpl.list.size());
                ValueObjectImpl.list.remove(0);
                System.out.println("list size " + Thread.currentThread().getName() + ": " + ValueObjectImpl.list.size());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
