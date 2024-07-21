package com.github.javarar.lucky.ticket;

import com.github.javarar.lucky.ticket.predicate.LeningradTicketNumberPredicate;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class LeningradTicketNumberPredicateTest {

    @Test
    @Parameters(method = "testParameters")
    public void test(long number, int digitNumber, String expected) {
        AtomicReference<String> actual = new AtomicReference<>();
        Consumer<String> consumer = actual::set;

        LeningradTicketNumberPredicate predicate = new LeningradTicketNumberPredicate(consumer);
        predicate.test(number, digitNumber);

        assertEquals(expected, actual.get());
    }

    @SuppressWarnings("unused")
    private Object[][] testParameters() {
        return new Object[][] {
                new Object[] {1, 3, null},
                new Object[] {154901, 6, "154901"},
                new Object[] {1213, 4, null},
                new Object[] {101, 3, "101"},
        };
    }
}
