package com.itgreenzone.patterns.behavioral.templatemethod.after;

import com.itgreenzone.patterns.behavioral.templatemethod.Account;
import com.itgreenzone.patterns.behavioral.templatemethod.Figures;

import java.math.BigDecimal;
import java.util.List;

/** CONCRETE CLASS: 4.5% p.a., no fees, statements sent by post. */
public class FixedDepositStatement extends StatementGenerator {

    public FixedDepositStatement(List<String> outbox) {
        super(outbox);
    }

    @Override
    protected BigDecimal calculateInterest(Account account, BigDecimal balance) {
        return monthlyRate(balance, "4.5");
    }

    @Override
    protected String format(Account a, Figures f) {
        return "FIXED DEPOSIT " + a.number() + " | interest " + f.interest() + " | closing " + f.closing();
    }

    @Override
    protected String channel() {
        return "POST";
    }
}
