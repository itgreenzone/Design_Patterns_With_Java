package com.itgreenzone.patterns.foundations.solid.solution;

import com.itgreenzone.patterns.foundations.solid.dip.after.EmailSender;

import java.math.BigDecimal;

/** Reference solution: coordinates; every collaborator is injected (DIP). */
public class RenewalService {

    private final RenewalPremiumCalculator calculator;
    private final RenewalLetterFormatter formatter;
    private final EmailSender sender;

    public RenewalService(RenewalPremiumCalculator calculator, RenewalLetterFormatter formatter, EmailSender sender) {
        this.calculator = calculator;
        this.formatter = formatter;
        this.sender = sender;
    }

    public BigDecimal renew(String email, BigDecimal currentPremium, int claimsLastYear) {
        BigDecimal premium = calculator.renewalPremium(currentPremium, claimsLastYear);
        sender.send(email, formatter.format(premium));
        return premium;
    }
}
