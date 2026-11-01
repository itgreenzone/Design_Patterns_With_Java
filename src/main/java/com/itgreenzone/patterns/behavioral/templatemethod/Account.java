package com.itgreenzone.patterns.behavioral.templatemethod;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/** An account at month end. For credit cards and loans the balance is negative (owed). */
public record Account(String number, String holder, BigDecimal openingBalance, List<Transaction> transactions) {

    public Account {
        openingBalance = openingBalance.setScale(2, RoundingMode.HALF_UP);
        transactions = List.copyOf(transactions);
    }
}
