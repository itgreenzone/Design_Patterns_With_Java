package com.itgreenzone.patterns.behavioral.strategy.modern;

import java.math.BigDecimal;

public record Receipt(String method, BigDecimal amount, BigDecimal fee) {

    public BigDecimal total() {
        return amount.add(fee);
    }
}
