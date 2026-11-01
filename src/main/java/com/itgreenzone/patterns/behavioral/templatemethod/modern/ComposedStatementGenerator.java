package com.itgreenzone.patterns.behavioral.templatemethod.modern;

import com.itgreenzone.patterns.behavioral.templatemethod.Account;
import com.itgreenzone.patterns.behavioral.templatemethod.Figures;
import com.itgreenzone.patterns.behavioral.templatemethod.Statement;
import com.itgreenzone.patterns.behavioral.templatemethod.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * MODERN VARIANT: a FINAL class owns the skeleton; the steps are injected.
 * Same idea as Spring's JdbcTemplate: the template runs the workflow and calls
 * back into the lambdas you pass. No subclassing needed.
 */
public final class ComposedStatementGenerator {

    private final StatementSteps steps;

    public ComposedStatementGenerator(StatementSteps steps) {
        this.steps = steps;
    }

    public Statement generate(Account account) {
        BigDecimal movements = account.transactions().stream().map(Transaction::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
        BigDecimal balance = account.openingBalance().add(movements);
        BigDecimal interest = steps.interest().apply(account, balance).setScale(2, RoundingMode.HALF_UP);
        BigDecimal fees = steps.fee().apply(balance).setScale(2, RoundingMode.HALF_UP);
        BigDecimal zero = BigDecimal.ZERO.setScale(2);
        BigDecimal closing = balance.add(interest).subtract(fees);
        Figures f = new Figures(account.openingBalance(), movements, interest, fees, zero, closing);
        return new Statement(account.number(), f, steps.format().apply(account, f));
    }
}
