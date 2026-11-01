package com.itgreenzone.patterns.behavioral.templatemethod.before;

import com.itgreenzone.patterns.behavioral.templatemethod.Account;
import com.itgreenzone.patterns.behavioral.templatemethod.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/** BEFORE: the whole workflow, copied. Compare with the other two generators. */
public class SavingsStatementGenerator {

    private final List<String> outbox;

    public SavingsStatementGenerator(List<String> outbox) {
        this.outbox = outbox;
    }

    public String generate(Account a) {
        BigDecimal balance = a.openingBalance();                          // load
        for (Transaction t : a.transactions()) {
            balance = balance.add(t.amount());
        }
        BigDecimal interest = balance.multiply(new BigDecimal("3"))      // interest
                .divide(new BigDecimal("1200"), 2, RoundingMode.HALF_UP);
        BigDecimal fees = balance.compareTo(new BigDecimal("1000")) < 0  // fees
                ? new BigDecimal("2.00") : BigDecimal.ZERO.setScale(2);
        BigDecimal closing = balance.add(interest).subtract(fees);
        String body = "SAVINGS " + a.number() + " | interest " + interest + " | fees " + fees
                + " | closing " + closing;                                 // format
        outbox.add("EMAIL to " + a.holder() + ": " + body);               // deliver
        return body;
    }
}
