package com.itgreenzone.patterns.foundations.solid.srp.before;

import com.itgreenzone.patterns.foundations.solid.Claim;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * BEFORE (breaks SRP): one class has THREE reasons to change –
 * validation rules (claims team), payout maths (actuaries) and email wording (marketing).
 */
public class ClaimService {

    private final List<String> outbox = new ArrayList<>();

    public BigDecimal process(Claim claim) {
        if (claim.amount() == null || claim.amount().signum() <= 0) {           // reason 1
            throw new IllegalArgumentException("Claim amount must be positive");
        }
        BigDecimal payout = claim.amount().multiply(new BigDecimal("0.90"))     // reason 2
                .setScale(2, RoundingMode.HALF_UP);
        outbox.add("To " + claim.customerEmail() + ": your claim " + claim.id()  // reason 3
                + " pays SGD " + payout);
        return payout;
    }

    public List<String> outbox() {
        return List.copyOf(outbox);
    }
}
