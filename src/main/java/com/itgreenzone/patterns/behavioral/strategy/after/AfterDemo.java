package com.itgreenzone.patterns.behavioral.strategy.after;

import java.math.BigDecimal;

/** Run me to see the Strategy pattern in action. */
public class AfterDemo {

    public static void main(String[] args) {
        BigDecimal amount = new BigDecimal("120.00");

        // The CLIENT picks the strategy; the Context just uses it.
        PaymentService service = new PaymentService(new CreditCardPayment("4111111111111111"));
        System.out.println(service.checkout(amount));

        service.setPaymentStrategy(new PayPalPayment("alice@example.com"));
        System.out.println(service.checkout(amount));

        service.setPaymentStrategy(new GrabPayPayment("+6591234567"));
        System.out.println(service.checkout(amount));

        // New method, zero changes to PaymentService:
        service.setPaymentStrategy(new PayNowPayment("+6591234567"));
        System.out.println(service.checkout(amount));
    }
}
