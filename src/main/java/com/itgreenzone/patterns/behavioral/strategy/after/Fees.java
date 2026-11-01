package com.itgreenzone.patterns.behavioral.strategy.after;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

/**
 * Small shared helpers. Keeping rounding in one place means every strategy
 * rounds money the same way (2 decimal places, HALF_UP).
 */
final class Fees {

    private Fees() {
    }

    static BigDecimal percent(BigDecimal amount, String rate) {
        return amount.multiply(new BigDecimal(rate)).setScale(2, RoundingMode.HALF_UP);
    }

    static String reference(String prefix) {
        return prefix + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
