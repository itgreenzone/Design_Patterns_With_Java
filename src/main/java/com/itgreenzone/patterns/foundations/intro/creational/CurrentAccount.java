package com.itgreenzone.patterns.foundations.intro.creational;

import java.math.BigDecimal;

/** A current (cheque) account charges SGD 5.00 per month. */
public record CurrentAccount(String accountNumber, String owner) implements Account {

    @Override
    public BigDecimal monthlyFee() {
        return new BigDecimal("5.00");
    }
}
