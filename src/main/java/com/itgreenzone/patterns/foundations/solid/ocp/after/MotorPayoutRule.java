package com.itgreenzone.patterns.foundations.solid.ocp.after;

import com.itgreenzone.patterns.foundations.solid.Claim;
import com.itgreenzone.patterns.foundations.solid.ClaimType;

import java.math.BigDecimal;

/** Motor: SGD 500 excess (deductible). */
public class MotorPayoutRule implements PayoutRule {

    public boolean supports(Claim claim) {
        return claim.type() == ClaimType.MOTOR;
    }

    public BigDecimal payout(Claim claim) {
        return claim.amount().subtract(new BigDecimal("500.00")).max(BigDecimal.ZERO.setScale(2));
    }
}
