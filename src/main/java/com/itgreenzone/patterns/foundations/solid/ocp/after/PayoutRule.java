package com.itgreenzone.patterns.foundations.solid.ocp.after;

import com.itgreenzone.patterns.foundations.solid.Claim;

import java.math.BigDecimal;

/** The extension point: each product brings its own rule. */
public interface PayoutRule {

    boolean supports(Claim claim);

    BigDecimal payout(Claim claim);
}
