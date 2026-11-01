package com.itgreenzone.patterns.behavioral.strategy.exercise;

import com.itgreenzone.patterns.behavioral.strategy.after.PaymentReceipt;
import com.itgreenzone.patterns.behavioral.strategy.after.PaymentStrategy;

import java.math.BigDecimal;

/**
 * HANDS-ON EXERCISE (≈20 minutes)
 *
 * The business wants a "Buy Now, Pay Later" (BNPL) option. Implement it as a
 * new Concrete Strategy WITHOUT changing PaymentService or any existing strategy.
 *
 * Rules:
 *  1. Constructor takes the customer's account ID; reject null or blank.
 *  2. name() returns "BNPL".
 *  3. Only orders from SGD 50.00 to SGD 3,000.00 (inclusive) are allowed;
 *     otherwise throw IllegalArgumentException.
 *  4. The customer pays no fee (fee = 0.00).
 *  5. The reference must start with "BNPL-".
 *  6. Add a method installment(BigDecimal amount) that returns the amount
 *     split into 3 equal payments, rounded to 2 decimals (HALF_UP).
 *
 * Then: use it with PaymentService in a small main method.
 * Reference solution: ..strategy.solution.BuyNowPayLaterPayment
 */
public class BuyNowPayLaterPayment implements PaymentStrategy {

    public BuyNowPayLaterPayment(String accountId) {
        // TODO 1: validate and store accountId
    }

    @Override
    public String name() {
        // TODO 2
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public PaymentReceipt pay(BigDecimal amount) {
        // TODO 3–5
        throw new UnsupportedOperationException("TODO");
    }

    public BigDecimal installment(BigDecimal amount) {
        // TODO 6
        throw new UnsupportedOperationException("TODO");
    }
}
