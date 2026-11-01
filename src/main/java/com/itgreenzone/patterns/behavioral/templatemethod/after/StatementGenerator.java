package com.itgreenzone.patterns.behavioral.templatemethod.after;

import com.itgreenzone.patterns.behavioral.templatemethod.Account;
import com.itgreenzone.patterns.behavioral.templatemethod.Figures;
import com.itgreenzone.patterns.behavioral.templatemethod.Statement;
import com.itgreenzone.patterns.behavioral.templatemethod.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * ABSTRACT CLASS: generate() is the TEMPLATE METHOD. It is final, so the order
 * of steps can't be changed by a subclass. Subclasses fill in:
 *   - PRIMITIVE OPERATIONS (abstract, must implement): calculateInterest, format
 *   - HOOKS (optional, have defaults): monthlyFee, applyPromotions, extraSection, channel
 * Hollywood Principle: subclasses never call generate's steps; generate calls them.
 */
public abstract class StatementGenerator {

    private final List<String> outbox;

    protected StatementGenerator(List<String> outbox) {
        this.outbox = outbox;
    }

    /** TEMPLATE METHOD: load → interest/charges → fees → promotions → format → deliver. */
    public final Statement generate(Account account) {
        List<Transaction> txns = loadTransactions(account);
        BigDecimal movements = txns.stream().map(Transaction::amount).reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal balance = account.openingBalance().add(movements);

        BigDecimal interest = calculateInterest(account, balance).setScale(2, RoundingMode.HALF_UP);
        BigDecimal fees = monthlyFee(balance).setScale(2, RoundingMode.HALF_UP);
        BigDecimal promotion = applyPromotions(account, txns).setScale(2, RoundingMode.HALF_UP);
        BigDecimal closing = balance.add(interest).subtract(fees).add(promotion);

        Figures figures = new Figures(account.openingBalance(), movements, interest, fees, promotion, closing);
        String body = format(account, figures) + extraSection(account, figures).map(s -> "\n" + s).orElse("");
        deliver(account, body);
        return new Statement(account.number(), figures, body);
    }

    // ---- fixed steps (private: not overridable) ----

    private List<Transaction> loadTransactions(Account account) {
        return account.transactions().stream().sorted(Comparator.comparing(Transaction::date)).toList();
    }

    private void deliver(Account account, String body) {
        outbox.add(channel() + " to " + account.holder() + ": " + body.lines().findFirst().orElse(""));
    }

    // ---- primitive operations: every subclass MUST implement ----

    /** Interest earned (positive) or charges (negative) for the month. */
    protected abstract BigDecimal calculateInterest(Account account, BigDecimal balance);

    protected abstract String format(Account account, Figures figures);

    // ---- hooks: optional, with sensible defaults ----

    protected BigDecimal monthlyFee(BigDecimal balance) {
        return BigDecimal.ZERO;
    }

    protected BigDecimal applyPromotions(Account account, List<Transaction> txns) {
        return BigDecimal.ZERO;
    }

    protected Optional<String> extraSection(Account account, Figures figures) {
        return Optional.empty();
    }

    protected String channel() {
        return "EMAIL";
    }

    // ---- helper for subclasses ----

    /** amount × annualPercent / 100 / 12, 2 dp HALF_UP. */
    protected static BigDecimal monthlyRate(BigDecimal amount, String annualPercent) {
        return amount.multiply(new BigDecimal(annualPercent)).divide(new BigDecimal("1200"), 2, RoundingMode.HALF_UP);
    }
}
