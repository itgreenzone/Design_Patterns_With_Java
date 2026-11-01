package com.itgreenzone.patterns.behavioral.templatemethod.before;

import com.itgreenzone.patterns.behavioral.templatemethod.Account;
import com.itgreenzone.patterns.behavioral.templatemethod.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/** BEFORE: a third copy of the same workflow. */
public class FixedDepositStatementGenerator {

    private final List<String> outbox;

    public FixedDepositStatementGenerator(List<String> outbox) {
        this.outbox = outbox;
    }

    public String generate(Account a) {
        BigDecimal balance = a.openingBalance();
        for (Transaction t : a.transactions()) {
            balance = balance.add(t.amount());
        }
        BigDecimal interest = balance.multiply(new BigDecimal("4.5"))
                .divide(new BigDecimal("1200"), 2, RoundingMode.HALF_UP);
        BigDecimal closing = balance.add(interest);
        String body = "FIXED DEPOSIT " + a.number() + " | interest " + interest + " | closing " + closing;
        outbox.add("POST to " + a.holder() + ": " + body);
        return body;
    }
}
