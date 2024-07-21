package com.github.javarar.rejected.task;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int queueSize = 3;

        Executor executors = CustomThreadExecutors.logRejectedThreadPoolExecutor(1, queueSize - 1, 500L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(queueSize));
        for (int i = 0; i < queueSize * 3; i++) {
            int finalI = i;
            executors.execute(() -> {
                System.out.printf("Task %d %n", finalI);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        Thread.sleep(1500);
        System.exit(0);
    }
}
