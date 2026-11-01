package com.itgreenzone.patterns.behavioral.templatemethod.solution;

import com.itgreenzone.patterns.behavioral.templatemethod.Account;
import com.itgreenzone.patterns.behavioral.templatemethod.Figures;
import com.itgreenzone.patterns.behavioral.templatemethod.after.StatementGenerator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

/** Reference solution: a new concrete class; the amortisation step plugs in via a hook. */
public class LoanStatement extends StatementGenerator {

    private final BigDecimal instalment;

    public LoanStatement(List<String> outbox, BigDecimal instalment) {
        super(outbox);
        this.instalment = instalment.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    protected BigDecimal calculateInterest(Account account, BigDecimal balance) {
        return balance.signum() < 0 ? monthlyRate(balance.negate(), "6").negate() : BigDecimal.ZERO;
    }

    @Override
    protected String format(Account a, Figures f) {
        return "LOAN " + a.number() + " | interest " + f.interest() + " | closing " + f.closing();
    }

    @Override
    protected Optional<String> extraSection(Account a, Figures f) {
        BigDecimal interest = f.interest().abs();
        BigDecimal principal = instalment.subtract(interest);
        BigDecimal remaining = f.closing().negate().subtract(instalment);
        return Optional.of("Instalment " + instalment + " = interest " + interest + " + principal " + principal
                + " | remaining " + remaining);
    }
}
