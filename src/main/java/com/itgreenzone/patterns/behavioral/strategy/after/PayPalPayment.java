package com.itgreenzone.patterns.behavioral.strategy.after;

import java.math.BigDecimal;

/**
 * CONCRETE STRATEGY: PayPal (3.4% + SGD 0.50 fixed fee).
 */
public class PayPalPayment implements PaymentStrategy {

    private static final BigDecimal FIXED_FEE = new BigDecimal("0.50");

    private final String email;

    public PayPalPayment(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("PayPal needs a valid email");
        }
        this.email = email;
    }

    @Override
    public String name() {
        return "PAYPAL";
    }

    @Override
    public PaymentReceipt pay(BigDecimal amount) {
        BigDecimal fee = Fees.percent(amount, "0.034").add(FIXED_FEE);
        System.out.println("Redirecting to PayPal for " + email);
        return new PaymentReceipt(name(), amount, fee, Fees.reference("PP"));
    }
}
