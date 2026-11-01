package com.itgreenzone.patterns.foundations.solid.ocp.after;

import com.itgreenzone.patterns.foundations.solid.Claim;
import com.itgreenzone.patterns.foundations.solid.ClaimType;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Health: 10% co-payment, so the insurer pays 90%. */
public class HealthPayoutRule implements PayoutRule {

    public boolean supports(Claim claim) {
        return claim.type() == ClaimType.HEALTH;
    }

    public BigDecimal payout(Claim claim) {
        return claim.amount().multiply(new BigDecimal("0.90")).setScale(2, RoundingMode.HALF_UP);
    }
}
