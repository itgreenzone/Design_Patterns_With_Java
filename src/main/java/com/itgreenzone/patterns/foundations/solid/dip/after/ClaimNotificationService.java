package com.itgreenzone.patterns.foundations.solid.dip.after;

import com.itgreenzone.patterns.foundations.solid.Claim;

import java.util.Objects;

/** AFTER (DIP): receives its EmailSender – easy to test, easy to swap. */
public class ClaimNotificationService {

    private final EmailSender sender;

    public ClaimNotificationService(EmailSender sender) {
        this.sender = Objects.requireNonNull(sender);
    }

    public void claimReceived(Claim claim) {
        sender.send(claim.customerEmail(), "We received claim " + claim.id());
    }
}
