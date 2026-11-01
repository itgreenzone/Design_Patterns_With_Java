package com.itgreenzone.patterns.behavioral.strategy.modern;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * MODERN JAVA 21 variant of the Strategy interface.
 *
 * When a strategy is just ONE function with no state, a @FunctionalInterface
 * lets us write each strategy as a lambda instead of a whole class.
 */
@FunctionalInterface
public interface FeeStrategy {

    BigDecimal feeFor(BigDecimal amount);

    /** Factory for the most common case: a percentage fee. */
    static FeeStrategy percent(String rate) {
        BigDecimal r = new BigDecimal(rate);
        return amount -> amount.multiply(r).setScale(2, RoundingMode.HALF_UP);
    }

    /** No fee at all. */
    static FeeStrategy free() {
        return amount -> BigDecimal.ZERO.setScale(2);
    }

    /** Compose strategies: "this fee PLUS a fixed amount" – no new class needed. */
    default FeeStrategy plus(String fixed) {
        BigDecimal f = new BigDecimal(fixed);
        return amount -> feeFor(amount).add(f);
    }
}
