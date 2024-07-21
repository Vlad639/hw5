package com.github.javarar.rejected.task;

import java.util.concurrent.*;
import java.util.function.BiConsumer;


public final class CustomThreadExecutors extends ThreadPoolExecutor {

    public CustomThreadExecutors(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, BiConsumer<Runnable, Executor> rejectHandler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        setRejectedExecutionHandler(rejectHandler::accept);
    }

    public static Executor logRejectedThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        return handleRejectedThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, (runnable, executor) -> System.out.printf("Task '%s' for executor '%s' was rejected %n", runnable, executor));
    }

    public static Executor handleRejectedThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, BiConsumer<Runnable, Executor> rejectHandler) {
        return new CustomThreadExecutors(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, rejectHandler);
    }
}
