package com.itgreenzone.patterns.foundations.solid.solution;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Reference solution: pricing only (one reason to change: pricing rules). */
public class RenewalPremiumCalculator {

    public BigDecimal renewalPremium(BigDecimal currentPremium, int claimsLastYear) {
        BigDecimal factor = claimsLastYear == 0 ? new BigDecimal("0.95") : new BigDecimal("1.10");
        return currentPremium.multiply(factor).setScale(2, RoundingMode.HALF_UP);
    }
}
