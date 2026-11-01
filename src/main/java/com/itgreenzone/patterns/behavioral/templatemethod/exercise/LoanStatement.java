package com.itgreenzone.patterns.behavioral.templatemethod.exercise;

import com.itgreenzone.patterns.behavioral.templatemethod.Account;
import com.itgreenzone.patterns.behavioral.templatemethod.Figures;
import com.itgreenzone.patterns.behavioral.templatemethod.after.StatementGenerator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * HANDS-ON EXERCISE (≈20 minutes)
 *
 * A loan account has a negative balance (principal owed). Implement:
 *   - calculateInterest: 6% p.a. on the amount owed, as a NEGATIVE amount
 *     (use monthlyRate(owed, "6").negate())
 *   - format: "LOAN <number> | interest <interest> | closing <closing>"
 *   - extraSection (the HOOK) with the amortisation step:
 *     "Instalment <instalment> = interest <i> + principal <instalment - i> | remaining <closing.negate() - instalment>"
 *     where i = figures.interest().abs(). Expected output is in TemplateMethodTest.
 * StatementGenerator must not change. Reference: ..templatemethod.solution.LoanStatement
 */
public class LoanStatement extends StatementGenerator {

    private final BigDecimal instalment;

    public LoanStatement(List<String> outbox, BigDecimal instalment) {
        super(outbox);
        this.instalment = instalment;
    }

    @Override
    protected BigDecimal calculateInterest(Account account, BigDecimal balance) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    protected String format(Account account, Figures figures) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    protected Optional<String> extraSection(Account account, Figures figures) {
        throw new UnsupportedOperationException("TODO: use instalment " + instalment);
    }
}
