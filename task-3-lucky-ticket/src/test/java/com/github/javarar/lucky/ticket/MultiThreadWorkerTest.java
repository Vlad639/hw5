package com.github.javarar.lucky.ticket;

import com.github.javarar.lucky.ticket.predicate.MoscowTicketNumberPredicate;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.stream.LongStream;

import static org.junit.Assert.assertArrayEquals;

public class MultiThreadWorkerTest {

    @Test(timeout = 10_000) //Сравниваем результаты работы однопоточного и многопоточного алгоритмов
    public void name() throws InterruptedException {
        List<Long> numbers = LongStream.rangeClosed(1, 99999).boxed().toList();

        List<String> moscowNumbersSingleThread = new ArrayList<>();
        Consumer<String> moscowPredicateConsumer = moscowNumbersSingleThread::add;

        SingleThreadWorker singleThreadWorker = new SingleThreadWorker(5, numbers, new MoscowTicketNumberPredicate(moscowPredicateConsumer));
        singleThreadWorker.start();
        singleThreadWorker.join();

        Collections.sort(moscowNumbersSingleThread);

        List<String> moscowNumbersMultiThread = new CopyOnWriteArrayList<>();
        Consumer<String> moscowNumbersMultiThreadConsumer = moscowNumbersMultiThread::add;
        MultiThreadWorker multiThreadWorker = new MultiThreadWorker(5, numbers, new MoscowTicketNumberPredicate(moscowNumbersMultiThreadConsumer));
        multiThreadWorker.start();
        multiThreadWorker.join();

        Collections.sort(moscowNumbersMultiThread);

        assertArrayEquals(moscowNumbersSingleThread.toArray(), moscowNumbersMultiThread.toArray());
    }
}
