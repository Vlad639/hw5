package com.github.javarar.rejected.task;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

public class CustomTreadExecutorsTest {

    @Test
    public void threadPoolDoesNotThrowExceptionOnQueueOverflow() throws InterruptedException {
        int queueSize = 3;
        AtomicInteger rejected = new AtomicInteger();
        BiConsumer<Runnable, Executor> rejectHandler = (runnable, executor) -> {
            System.out.printf("Task '%s' for executor '%s' was rejected %n", runnable, executor);
            rejected.incrementAndGet();
        };

        Executor executors = CustomThreadExecutors.handleRejectedThreadPoolExecutor(1, queueSize - 1, 500L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(queueSize), rejectHandler);
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

        assert rejected.get() > 0;
    }
}
