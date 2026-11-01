package com.itgreenzone.patterns.behavioral.templatemethod;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

/** A posted transaction: credits positive, debits negative (2 dp). */
public record Transaction(LocalDate date, String description, BigDecimal amount) {

    public Transaction {
        amount = amount.setScale(2, RoundingMode.HALF_UP);
    }
}
