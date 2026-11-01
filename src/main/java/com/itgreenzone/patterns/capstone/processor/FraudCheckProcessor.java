package com.itgreenzone.patterns.capstone.processor;

import java.math.BigDecimal;

/** DECORATOR (L12): declines charges above a limit before they reach the gateway. */
public class FraudCheckProcessor implements PaymentProcessor {

    private final PaymentProcessor inner;
    private final BigDecimal limit;

    public FraudCheckProcessor(PaymentProcessor inner, BigDecimal limit) {
        this.inner = inner;
        this.limit = limit;
    }

    @Override
    public String charge(String orderId, BigDecimal amount) {
        if (amount.compareTo(limit) > 0) {
            throw new PaymentDeclinedException("Fraud check: " + orderId + " " + amount + " exceeds " + limit);
        }
        return inner.charge(orderId, amount);
    }

    @Override
    public void refund(String reference, BigDecimal amount) {
        inner.refund(reference, amount);
    }
}
