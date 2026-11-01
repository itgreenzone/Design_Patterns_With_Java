package com.itgreenzone.patterns.behavioral.iterator;

import java.math.BigDecimal;
import java.time.LocalDate;

/** amount < 0 is a debit, amount > 0 is a credit. */
public record Transaction(String id, LocalDate date, BigDecimal amount, String description) {

    public boolean isDebit() {
        return amount.signum() < 0;
    }
}
