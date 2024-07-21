package com.github.javarar.lucky.ticket.predicate;

import java.util.function.Consumer;

public abstract class AbstractTicketNumberPredicate {

    private final Consumer<String> successConsumer;

    public AbstractTicketNumberPredicate(Consumer<String> successConsumer) {
        this.successConsumer = successConsumer;
    }

    public void test(long number, int digitNumber) {
        String ticketNumber = toTicketNumber(number, digitNumber);
        if (test(ticketNumber)) {
            successConsumer.accept(ticketNumber);
        }
    }

    protected String toTicketNumber(long number, int digitNumber) {
        return String.format("%0" + digitNumber + "d", number);
    }

    protected abstract boolean test(String number);


}
