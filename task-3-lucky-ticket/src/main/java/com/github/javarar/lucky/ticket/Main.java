package com.github.javarar.lucky.ticket;

import com.github.javarar.lucky.ticket.predicate.AbstractTicketNumberPredicate;
import com.github.javarar.lucky.ticket.predicate.LeningradTicketNumberPredicate;
import com.github.javarar.lucky.ticket.predicate.MoscowTicketNumberPredicate;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.stream.LongStream;

public class Main {

    private final static int DIGIT_NUMBERS = 5;

    private final static List<String> moscowLuckyNumbers = new CopyOnWriteArrayList<>();
    private final static List<String> leningradLuckyNumbers = new CopyOnWriteArrayList<>();


    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();

        long allNumbers = (long) Math.pow(10, DIGIT_NUMBERS)  - 1;
        List<Long> numbers = LongStream.rangeClosed(1L, allNumbers).boxed().toList();

        Consumer<String> moscowNumbersMultiThreadConsumer = moscowLuckyNumbers::add;
        Consumer<String> leningradNumbersMultiThreadConsumer = leningradLuckyNumbers::add;

        AbstractTicketNumberPredicate[] predicates = new AbstractTicketNumberPredicate[] {
                new MoscowTicketNumberPredicate(moscowNumbersMultiThreadConsumer),
                new LeningradTicketNumberPredicate(leningradNumbersMultiThreadConsumer)
        };

        MultiThreadWorker multiThreadWorker = new MultiThreadWorker(DIGIT_NUMBERS, numbers, predicates);
        multiThreadWorker.start();
        multiThreadWorker.join();

        System.out.printf("Вероятность выпадения счастливого Московского билета %.3f %n", moscowLuckyNumbers.size() / (double) allNumbers);
        System.out.printf("Найденные Московские билеты: %s %n", moscowLuckyNumbers);

        System.out.println();
        System.out.printf("Вероятность выпадения счастливого Ленинградского билета %.3f %n", leningradLuckyNumbers.size() / (double) allNumbers);
        System.out.printf("Найденные Ленинградские билеты: %s %n", leningradLuckyNumbers);

        System.out.println();
        System.out.println("=============================");
        System.out.printf("Время выполнения: %d млс.", System.currentTimeMillis() - start);

    }
}
