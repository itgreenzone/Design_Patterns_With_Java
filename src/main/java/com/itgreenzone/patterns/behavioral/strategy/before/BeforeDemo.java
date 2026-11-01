package com.itgreenzone.patterns.behavioral.strategy.before;

import java.math.BigDecimal;

/** Run me to see the "before" checkout in action. */
public class BeforeDemo {

    public static void main(String[] args) {
        PaymentService service = new PaymentService();
        BigDecimal amount = new BigDecimal("120.00");

        System.out.println(service.checkout(PaymentType.CREDIT_CARD, amount, "4111111111111111"));
        System.out.println(service.checkout(PaymentType.PAYPAL, amount, "alice@example.com"));
        System.out.println(service.checkout(PaymentType.GRABPAY, amount, "+6591234567"));

        // Want PayNow? You must open PaymentService and add another branch.
    }
}
