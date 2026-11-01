package com.itgreenzone.patterns.foundations.solid.ocp.before;

import com.itgreenzone.patterns.foundations.solid.Claim;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * BEFORE (breaks OCP): every new product means editing this switch,
 * and re-testing every existing branch.
 */
public class PayoutCalculator {

    public BigDecimal payout(Claim claim) {
        BigDecimal amount = claim.amount();
        return switch (claim.type()) {
            case MOTOR -> amount.subtract(new BigDecimal("500.00")).max(BigDecimal.ZERO.setScale(2));
            case TRAVEL -> amount.min(new BigDecimal("5000.00"));
            case HEALTH -> amount.multiply(new BigDecimal("0.90")).setScale(2, RoundingMode.HALF_UP);
        };
    }
}
