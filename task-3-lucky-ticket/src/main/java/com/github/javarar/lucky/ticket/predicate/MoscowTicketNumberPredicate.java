package com.github.javarar.lucky.ticket.predicate;

import java.util.function.Consumer;

public class MoscowTicketNumberPredicate extends AbstractTicketNumberPredicate {
    public MoscowTicketNumberPredicate(Consumer<String> successConsumer) {
        super(successConsumer);
    }

    @Override
    protected boolean test(String number) {
        long evenSum = 0;
        long oddSum = 0;
        for (int i = 0; i < number.length(); i++) {
            long value = Character.getNumericValue(number.charAt(i));
            if (i % 2 == 0) {
                evenSum += value;
            } else {
                oddSum += value;
            }
        }

        return evenSum == oddSum;
    }
}
