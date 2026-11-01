package com.itgreenzone.patterns.behavioral.state;

import com.itgreenzone.patterns.behavioral.state.after.PaymentOrder;
import com.itgreenzone.patterns.behavioral.state.modern.EnumOrder;
import com.itgreenzone.patterns.behavioral.state.modern.OrderStatus;
import com.itgreenzone.patterns.behavioral.state.solution.ReturnableOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StateTest {

    private static final LocalDate DAY = LocalDate.of(2026, 11, 1);

    private PaymentOrder order;

    @BeforeEach
    void setUp() {
        order = new PaymentOrder("ORD-1", new BigDecimal("249.90"));
    }

    /** Drives a fresh order to the named status. */
    private static PaymentOrder orderIn(String status) {
        PaymentOrder o = new PaymentOrder("ORD-X", new BigDecimal("100"));
        switch (status) {
            case "NEW" -> { }
            case "PAID" -> o.pay();
            case "SHIPPED" -> { o.pay(); o.ship(); }
            case "DELIVERED" -> { o.pay(); o.ship(); o.deliver(DAY); }
            case "CANCELLED" -> o.cancel();
            default -> throw new IllegalArgumentException(status);
        }
        return o;
    }

    private static void fire(PaymentOrder o, String event) {
        switch (event) {
            case "pay" -> o.pay();
            case "ship" -> o.ship();
            case "deliver" -> o.deliver(DAY);
            case "cancel" -> o.cancel();
            default -> throw new IllegalArgumentException(event);
        }
    }

    @Nested
    class LegalTransitions {

        @ParameterizedTest
        @CsvSource({"NEW,pay,PAID", "PAID,ship,SHIPPED", "SHIPPED,deliver,DELIVERED", "NEW,cancel,CANCELLED", "PAID,cancel,CANCELLED"})
        void moveToTheNextState(String from, String event, String to) {
            PaymentOrder o = orderIn(from);
            fire(o, event);
            assertEquals(to, o.status());
        }

        @Test
        void happyPathHistory() {
            order.pay();
            order.ship();
            order.deliver(DAY);
            assertEquals(List.of("NEW -> PAID", "PAID -> SHIPPED", "SHIPPED -> DELIVERED"), order.history());
        }

        @Test
        void cancellingAPaidOrderRefundsInFull() {
            order.pay();
            order.cancel();
            assertEquals(new BigDecimal("249.90"), order.refunded());
        }

        @Test
        void cancellingANewOrderRefundsNothing() {
            order.cancel();
            assertEquals(new BigDecimal("0.00"), order.refunded());
        }
    }

    @Nested
    class IllegalTransitions {

        @ParameterizedTest
        @CsvSource({"NEW,ship", "NEW,deliver", "PAID,pay", "PAID,deliver", "SHIPPED,pay", "SHIPPED,ship",
                "SHIPPED,cancel", "DELIVERED,pay", "DELIVERED,ship", "DELIVERED,deliver", "DELIVERED,cancel",
                "CANCELLED,pay", "CANCELLED,ship", "CANCELLED,deliver", "CANCELLED,cancel"})
        void throwAndLeaveTheStateUnchanged(String from, String event) {
            PaymentOrder o = orderIn(from);
            IllegalStateException e = assertThrows(IllegalStateException.class, () -> fire(o, event));
            assertEquals("Cannot " + event + " an order that is " + from, e.getMessage());
            assertEquals(from, o.status());
        }
    }

    @Test
    void beforeVersionWronglyCancelsAShippedOrder() {
        var before = new com.itgreenzone.patterns.behavioral.state.before.PaymentOrder(new BigDecimal("10"));
        before.pay();
        before.ship();
        before.cancel();                       // should have thrown
        assertEquals(com.itgreenzone.patterns.behavioral.state.before.PaymentOrder.Status.CANCELLED, before.status());
    }

    @Test
    void enumStateMachineFollowsTheSameRules() {
        EnumOrder e = new EnumOrder(new BigDecimal("80"));
        e.pay();
        e.cancel();
        assertEquals(OrderStatus.CANCELLED, e.status());
        assertEquals(new BigDecimal("80.00"), e.refunded());

        EnumOrder shipped = new EnumOrder(new BigDecimal("80"));
        shipped.pay();
        shipped.ship();
        assertThrows(IllegalStateException.class, shipped::cancel);
        assertEquals(OrderStatus.SHIPPED, shipped.status());
    }

    @Nested
    class ReturnsExercise {

        private ReturnableOrder delivered() {
            ReturnableOrder r = new ReturnableOrder("ORD-R", new BigDecimal("59.99"));
            r.pay();
            r.ship();
            r.deliver(DAY);
            return r;
        }

        @Test
        void returnWithinFourteenDaysRefunds() {
            ReturnableOrder r = delivered();
            r.requestReturn(DAY.plusDays(14));
            assertEquals("RETURNED", r.status());
            assertEquals(new BigDecimal("59.99"), r.refunded());
        }

        @Test
        void returnAfterTheWindowIsRejected() {
            ReturnableOrder r = delivered();
            IllegalStateException e = assertThrows(IllegalStateException.class, () -> r.requestReturn(DAY.plusDays(15)));
            assertEquals("Return window closed", e.getMessage());
            assertEquals("DELIVERED", r.status());
        }

        @Test
        void cannotReturnBeforeDelivery() {
            ReturnableOrder r = new ReturnableOrder("ORD-R", new BigDecimal("59.99"));
            r.pay();
            assertThrows(IllegalStateException.class, () -> r.requestReturn(DAY));
        }

        @Test
        void returnedIsTerminal() {
            ReturnableOrder r = delivered();
            r.requestReturn(DAY);
            assertThrows(IllegalStateException.class, r::cancel);
        }
    }
}
