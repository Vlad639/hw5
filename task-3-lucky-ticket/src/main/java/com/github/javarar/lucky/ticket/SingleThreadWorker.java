package com.github.javarar.lucky.ticket;

import com.github.javarar.lucky.ticket.predicate.AbstractTicketNumberPredicate;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class SingleThreadWorker extends Thread {

    private final int digitNumbers;

    private final Queue<Long> numbers;
    private final AbstractTicketNumberPredicate[] predicates;

    public SingleThreadWorker(int digitNumbers, List<Long> numbers, AbstractTicketNumberPredicate ... predicates) {
        this.digitNumbers = digitNumbers;
        this.numbers = new ArrayDeque<>(numbers);
        this.predicates = predicates;
    }

    @Override
    public void run() {
        Long current = numbers.poll();
        while (current != null) {
            for (AbstractTicketNumberPredicate predicate: predicates) {
                predicate.test(current, digitNumbers);
            }
            current = numbers.poll();
        }
    }
}
