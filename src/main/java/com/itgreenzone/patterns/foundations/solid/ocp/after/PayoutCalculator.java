package com.itgreenzone.patterns.foundations.solid.ocp.after;

import com.itgreenzone.patterns.foundations.solid.Claim;

import java.math.BigDecimal;
import java.util.List;

/**
 * AFTER (OCP): closed for modification, open for extension.
 * A new product = a new PayoutRule passed in; this class never changes.
 */
public class PayoutCalculator {

    private final List<PayoutRule> rules;

    public PayoutCalculator(List<PayoutRule> rules) {
        this.rules = List.copyOf(rules);
    }

    public BigDecimal payout(Claim claim) {
        return rules.stream()
                .filter(r -> r.supports(claim))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No payout rule for " + claim.type()))
                .payout(claim);
    }
}
