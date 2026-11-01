package com.itgreenzone.patterns.behavioral.strategy.after;

import java.math.BigDecimal;

/**
 * Immutable result of a payment, shared by all strategies.
 */
public record PaymentReceipt(String method, BigDecimal amount, BigDecimal fee, String reference) {

    public BigDecimal total() {
        return amount.add(fee);
    }
}
