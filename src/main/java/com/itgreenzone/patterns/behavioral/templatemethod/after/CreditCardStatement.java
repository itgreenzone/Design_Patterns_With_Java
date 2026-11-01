package com.itgreenzone.patterns.behavioral.templatemethod.after;

import com.itgreenzone.patterns.behavioral.templatemethod.Account;
import com.itgreenzone.patterns.behavioral.templatemethod.Figures;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/** CONCRETE CLASS: 24% p.a. charges on the amount owed; minimum payment 5% or 25.00. */
public class CreditCardStatement extends StatementGenerator {

    public CreditCardStatement(List<String> outbox) {
        super(outbox);
    }

    @Override
    protected BigDecimal calculateInterest(Account account, BigDecimal balance) {
        return balance.signum() < 0 ? monthlyRate(balance.negate(), "24").negate() : BigDecimal.ZERO;
    }

    @Override
    protected String format(Account a, Figures f) {
        BigDecimal owed = f.closing().signum() < 0 ? f.closing().negate() : BigDecimal.ZERO.setScale(2);
        BigDecimal minimum = owed.multiply(new BigDecimal("0.05")).setScale(2, RoundingMode.HALF_UP)
                .max(owed.min(new BigDecimal("25.00")));
        return "CARD " + a.number() + " | charges " + f.interest() + " | closing " + f.closing()
                + " | minimum due " + minimum;
    }
}
