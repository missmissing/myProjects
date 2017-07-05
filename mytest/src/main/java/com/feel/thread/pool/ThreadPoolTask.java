package com.feel.thread.pool;

/**
 * Created by lyan on 2017/2/27.
 */
public class ThreadPoolTask implements Runnable {
    private Object threadPoolTaskData;
    private static int consumerTaskSleepTime = 3000;

    ThreadPoolTask(Object tasks) {
        this.threadPoolTaskData = tasks;
    }

    public void run() {
        System.out.println("start.." + threadPoolTaskData);

        try {
            Thread.sleep(consumerTaskSleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("finish" + threadPoolTaskData);
        threadPoolTaskData = null;
    }
}