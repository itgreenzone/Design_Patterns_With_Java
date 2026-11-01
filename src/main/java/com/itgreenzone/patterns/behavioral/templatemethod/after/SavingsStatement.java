package com.itgreenzone.patterns.behavioral.templatemethod.after;

import com.itgreenzone.patterns.behavioral.templatemethod.Account;
import com.itgreenzone.patterns.behavioral.templatemethod.Figures;
import com.itgreenzone.patterns.behavioral.templatemethod.Transaction;

import java.math.BigDecimal;
import java.util.List;

/** CONCRETE CLASS: 3% p.a., 2.00 fee under 1,000.00, 5.00 bonus if no withdrawals. */
public class SavingsStatement extends StatementGenerator {

    public SavingsStatement(List<String> outbox) {
        super(outbox);
    }

    @Override
    protected BigDecimal calculateInterest(Account account, BigDecimal balance) {
        return balance.signum() > 0 ? monthlyRate(balance, "3") : BigDecimal.ZERO;
    }

    @Override
    protected BigDecimal monthlyFee(BigDecimal balance) {
        return balance.compareTo(new BigDecimal("1000")) < 0 ? new BigDecimal("2.00") : BigDecimal.ZERO;
    }

    @Override
    protected BigDecimal applyPromotions(Account account, List<Transaction> txns) {
        boolean noWithdrawals = txns.stream().allMatch(t -> t.amount().signum() >= 0);
        return noWithdrawals ? new BigDecimal("5.00") : BigDecimal.ZERO;
    }

    @Override
    protected String format(Account a, Figures f) {
        return "SAVINGS " + a.number() + " | interest " + f.interest() + " | fees " + f.fees()
                + " | bonus " + f.promotion() + " | closing " + f.closing();
    }
}
