package com.itgreenzone.patterns.foundations.solid.ocp.after;

import com.itgreenzone.patterns.foundations.solid.Claim;
import com.itgreenzone.patterns.foundations.solid.ClaimType;

import java.math.BigDecimal;

/** Travel: paid in full up to a SGD 5,000 cap. */
public class TravelPayoutRule implements PayoutRule {

    public boolean supports(Claim claim) {
        return claim.type() == ClaimType.TRAVEL;
    }

    public BigDecimal payout(Claim claim) {
        return claim.amount().min(new BigDecimal("5000.00"));
    }
}
