package com.itgreenzone.patterns.behavioral.templatemethod.before;

import com.itgreenzone.patterns.behavioral.templatemethod.Account;
import com.itgreenzone.patterns.behavioral.templatemethod.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/** BEFORE: same steps again. The rounding fix (HALF_UP) never reached this copy. */
public class CreditCardStatementGenerator {

    private final List<String> outbox;

    public CreditCardStatementGenerator(List<String> outbox) {
        this.outbox = outbox;
    }

    public String generate(Account a) {
        BigDecimal balance = a.openingBalance();
        for (Transaction t : a.transactions()) {
            balance = balance.add(t.amount());
        }
        BigDecimal owed = balance.signum() < 0 ? balance.negate() : BigDecimal.ZERO;
        BigDecimal charges = owed.multiply(new BigDecimal("24"))
                .divide(new BigDecimal("1200"), 2, RoundingMode.DOWN).negate();   // BUG: DOWN
        BigDecimal fees = BigDecimal.ZERO.setScale(2);
        BigDecimal closing = balance.add(charges).subtract(fees);
        String body = "CARD " + a.number() + " | charges " + charges + " | fees " + fees
                + " | closing " + closing;
        outbox.add("EMAIL to " + a.holder() + ": " + body);
        return body;
    }
}
