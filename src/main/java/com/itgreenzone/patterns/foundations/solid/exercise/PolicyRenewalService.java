package com.itgreenzone.patterns.foundations.solid.exercise;

import com.itgreenzone.patterns.foundations.solid.dip.before.SmtpClient;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * HANDS-ON EXERCISE (≈25 minutes) – this legacy class WORKS but breaks SRP and DIP.
 *
 *  - SRP: it calculates the renewal premium AND formats the letter AND sends it.
 *  - DIP: it creates a concrete SmtpClient with "new", so it can't be unit-tested.
 *
 * Your task: complete RenewalPremiumCalculator and RenewalLetterFormatter (below in
 * this package), then write a new RenewalService that receives them plus an
 * EmailSender (from dip.after) through its constructor.
 * Reference solution: ..solid.solution
 */
public class PolicyRenewalService {

    private final SmtpClient smtp = new SmtpClient();

    public BigDecimal renew(String email, BigDecimal currentPremium, int claimsLastYear) {
        BigDecimal factor = claimsLastYear == 0 ? new BigDecimal("0.95") : new BigDecimal("1.10");
        BigDecimal premium = currentPremium.multiply(factor).setScale(2, RoundingMode.HALF_UP);
        smtp.send(email, "Your renewal premium is SGD " + premium);
        return premium;
    }
}
