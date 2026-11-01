package com.itgreenzone.patterns.foundations.solid.srp.after;

import com.itgreenzone.patterns.foundations.solid.Claim;

import java.math.BigDecimal;

/** AFTER (SRP): the service only coordinates; each rule lives in its own class. */
public class ClaimService {

    private final ClaimValidator validator;
    private final PayoutCalculator calculator;
    private final ClaimNotifier notifier;

    public ClaimService(ClaimValidator validator, PayoutCalculator calculator, ClaimNotifier notifier) {
        this.validator = validator;
        this.calculator = calculator;
        this.notifier = notifier;
    }

    public BigDecimal process(Claim claim) {
        validator.validate(claim);
        BigDecimal payout = calculator.payout(claim);
        notifier.notifyPayout(claim, payout);
        return payout;
    }
}
