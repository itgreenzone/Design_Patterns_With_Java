package com.itgreenzone.patterns.behavioral.strategy.solution;

import com.itgreenzone.patterns.behavioral.strategy.after.PaymentReceipt;
import com.itgreenzone.patterns.behavioral.strategy.after.PaymentStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

/**
 * Reference solution for the BNPL exercise.
 * Notice that nothing in the "after" package had to change to support it.
 */
public class BuyNowPayLaterPayment implements PaymentStrategy {

    static final BigDecimal MIN = new BigDecimal("50.00");
    static final BigDecimal MAX = new BigDecimal("3000.00");
    private static final BigDecimal INSTALMENTS = BigDecimal.valueOf(3);

    private final String accountId;

    public BuyNowPayLaterPayment(String accountId) {
        if (accountId == null || accountId.isBlank()) {
            throw new IllegalArgumentException("BNPL needs an account ID");
        }
        this.accountId = accountId;
    }

    @Override
    public String name() {
        return "BNPL";
    }

    @Override
    public PaymentReceipt pay(BigDecimal amount) {
        // Business rule lives inside the strategy, not in the Context.
        if (amount.compareTo(MIN) < 0 || amount.compareTo(MAX) > 0) {
            throw new IllegalArgumentException("BNPL is only for orders between SGD 50 and SGD 3,000");
        }
        System.out.println("BNPL for " + accountId + ": 3 x " + installment(amount));
        String ref = "BNPL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return new PaymentReceipt(name(), amount, BigDecimal.ZERO.setScale(2), ref);
    }

    public BigDecimal installment(BigDecimal amount) {
        return amount.divide(INSTALMENTS, 2, RoundingMode.HALF_UP);
    }
}
