package com.github.javarar.lucky.ticket.predicate;

import java.util.function.Consumer;

public class LeningradTicketNumberPredicate extends AbstractTicketNumberPredicate {
    public LeningradTicketNumberPredicate(Consumer<String> successConsumer) {
        super(successConsumer);
    }

    @Override
    protected boolean test(String number) {
        int middle = number.length() / 2;
        long leftSum = 0;
        long rightSum = 0;

        for (int i = 0; i < middle; i++) {
            long leftValue = Character.getNumericValue(number.charAt(i));
            long rightValue = Character.getNumericValue(number.charAt(number.length() - i - 1));

            leftSum += leftValue;
            rightSum += rightValue;

        }

        return leftSum == rightSum;
    }
}
