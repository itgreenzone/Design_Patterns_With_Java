package com.itgreenzone.patterns.behavioral.strategy.after;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * CONTEXT.
 *
 * PaymentService handles what is the SAME for every payment (checking the amount,
 * logging) and DELEGATES what varies (how to charge, what fee) to a PaymentStrategy.
 * It has no if-else on payment type – the client chooses the strategy.
 */
public class PaymentService {

    private PaymentStrategy strategy;

    public PaymentService(PaymentStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "Payment strategy is required");
    }

    /** The client can swap the algorithm at runtime, e.g. when the user changes method. */
    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "Payment strategy is required");
    }

    public PaymentReceipt checkout(BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        PaymentReceipt receipt = strategy.pay(amount);
        System.out.println("Checkout complete via " + strategy.name() + ": " + receipt.reference());
        return receipt;
    }
}
