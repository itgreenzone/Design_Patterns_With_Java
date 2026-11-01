package com.itgreenzone.patterns.foundations.solid.srp.after;

import com.itgreenzone.patterns.foundations.solid.Claim;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** ONE job: tell the customer. Owned by marketing/communications. */
public class ClaimNotifier {

    private final List<String> outbox = new ArrayList<>();

    public void notifyPayout(Claim claim, BigDecimal payout) {
        outbox.add("To " + claim.customerEmail() + ": your claim " + claim.id() + " pays SGD " + payout);
    }

    public List<String> outbox() {
        return List.copyOf(outbox);
    }
}
