package com.github.javarar.lucky.ticket;

import com.github.javarar.lucky.ticket.predicate.MoscowTicketNumberPredicate;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class MoscowTicketNumberPredicateTest {

    @Test
    @Parameters(method = "testParameters")
    public void name(long number, int digitNumber, String expected) {
        AtomicReference<String> actual = new AtomicReference<>();
        Consumer<String> consumer = actual::set;

        MoscowTicketNumberPredicate predicate = new MoscowTicketNumberPredicate(consumer);
        predicate.test(number, digitNumber);

        assertEquals(expected, actual.get());
    }

    @SuppressWarnings("unused")
    private Object[][] testParameters() {
        return new Object[][] {
                new Object[] {1, 3, null},
                new Object[] {123123, 6, "123123"},
                new Object[] {1562, 4, "1562"},
                new Object[] {1234, 4, null},
        };
    }
}
