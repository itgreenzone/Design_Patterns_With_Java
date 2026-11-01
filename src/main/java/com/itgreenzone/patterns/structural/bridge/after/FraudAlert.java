package com.itgreenzone.patterns.structural.bridge.after;

import java.math.BigDecimal;

/** REFINED ABSTRACTION: urgent wording, same channels. */
public class FraudAlert extends Alert {

    private final BigDecimal amount;
    private final String merchant;

    public FraudAlert(NotificationChannel channel, BigDecimal amount, String merchant) {
        super(channel);
        this.amount = amount;
        this.merchant = merchant;
    }

    protected String subject() {
        return "Fraud alert";
    }

    protected String body() {
        return "URGENT: suspicious SGD " + amount + " at " + merchant;
    }
}
