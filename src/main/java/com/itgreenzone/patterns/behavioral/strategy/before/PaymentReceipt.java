package com.itgreenzone.patterns.behavioral.strategy.before;

import java.math.BigDecimal;

/**
 * Immutable result of a payment. A record gives us equals/hashCode/toString for free.
 */
public record PaymentReceipt(String method, BigDecimal amount, BigDecimal fee, String reference) {

    public BigDecimal total() {
        return amount.add(fee);
    }
}
