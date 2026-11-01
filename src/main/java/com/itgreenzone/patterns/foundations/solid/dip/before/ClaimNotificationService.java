package com.itgreenzone.patterns.foundations.solid.dip.before;

import com.itgreenzone.patterns.foundations.solid.Claim;

/**
 * BEFORE (breaks DIP): high-level policy creates its low-level detail with "new".
 * You cannot unit-test this without a mail server, or switch to SMS.
 */
public class ClaimNotificationService {

    private final SmtpClient smtp = new SmtpClient();

    public void claimReceived(Claim claim) {
        smtp.send(claim.customerEmail(), "We received claim " + claim.id());
    }
}
