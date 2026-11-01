package com.itgreenzone.patterns.behavioral.state.after;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Happy path, a paid cancellation with refund, and an illegal transition. */
public class AfterDemo {

    public static void main(String[] args) {
        PaymentOrder order = new PaymentOrder("ORD-1001", new BigDecimal("249.90"));
        order.pay();
        order.ship();
        order.deliver(LocalDate.of(2026, 11, 1));
        System.out.println(order.history());     // [NEW -> PAID, PAID -> SHIPPED, SHIPPED -> DELIVERED]

        PaymentOrder second = new PaymentOrder("ORD-1002", new BigDecimal("80.00"));
        second.pay();
        second.cancel();
        System.out.println(second.status() + " refunded " + second.refunded());   // CANCELLED refunded 80.00

        PaymentOrder third = new PaymentOrder("ORD-1003", new BigDecimal("15.00"));
        third.pay();
        third.ship();
        try {
            third.cancel();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());  // Cannot cancel an order that is SHIPPED
        }
    }
}
