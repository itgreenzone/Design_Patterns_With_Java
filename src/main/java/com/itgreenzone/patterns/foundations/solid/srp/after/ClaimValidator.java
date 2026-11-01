package com.itgreenzone.patterns.foundations.solid.srp.after;

import com.itgreenzone.patterns.foundations.solid.Claim;

/** ONE job: decide whether a claim is valid. Owned by the claims team. */
public class ClaimValidator {

    public void validate(Claim claim) {
        if (claim.amount() == null || claim.amount().signum() <= 0) {
            throw new IllegalArgumentException("Claim amount must be positive");
        }
    }
}
