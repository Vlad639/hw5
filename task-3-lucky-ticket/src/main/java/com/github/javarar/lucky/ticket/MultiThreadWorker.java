package com.github.javarar.lucky.ticket;

import com.github.javarar.lucky.ticket.predicate.AbstractTicketNumberPredicate;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class MultiThreadWorker extends Thread {

    private final long maxHandled;
    private final AtomicLong currentHandled;

    private final int digitCount;
    private final ExecutorService executorService;

    private final Queue<Long> numbers;
    private final AbstractTicketNumberPredicate[] predicates;

    public MultiThreadWorker(int digitCount, List<Long> numbers, AbstractTicketNumberPredicate... predicates) {
        this.digitCount = digitCount;
        this.numbers = new LinkedBlockingQueue<>(numbers);
        this.maxHandled = ((long) Math.pow(10, digitCount)) - 1;
        this.currentHandled = new AtomicLong();

        this.executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 5L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        this.predicates = predicates;
    }

    @Override
    public void run() {


        Long handle = numbers.poll();
        while (handle != null) {
            long finalHandle = handle;
            executorService.execute(() -> {
                for (AbstractTicketNumberPredicate predicate : predicates) {
                    predicate.test(finalHandle, digitCount);
                }
                currentHandled.incrementAndGet();
            });

            handle = numbers.poll();
        }

        long current = currentHandled.get();

        while (current < maxHandled) {
            current = currentHandled.get();
        }

        executorService.shutdown();
    }

}
