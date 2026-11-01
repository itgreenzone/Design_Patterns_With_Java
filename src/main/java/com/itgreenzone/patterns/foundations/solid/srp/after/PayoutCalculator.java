package com.itgreenzone.patterns.foundations.solid.srp.after;

import com.itgreenzone.patterns.foundations.solid.Claim;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** ONE job: payout maths (90% of the claim). Owned by the actuaries. */
public class PayoutCalculator {

    public BigDecimal payout(Claim claim) {
        return claim.amount().multiply(new BigDecimal("0.90")).setScale(2, RoundingMode.HALF_UP);
    }
}
