package com.itgreenzone.patterns.foundations.intro.creational;

import java.math.BigDecimal;

/** A savings account has no monthly fee. */
public record SavingsAccount(String accountNumber, String owner) implements Account {

    @Override
    public BigDecimal monthlyFee() {
        return BigDecimal.ZERO.setScale(2);
    }
}
