package com.itgreenzone.patterns.structural.bridge.after;

import java.math.BigDecimal;

/** REFINED ABSTRACTION. */
public class TransactionAlert extends Alert {

    private final BigDecimal amount;
    private final String merchant;

    public TransactionAlert(NotificationChannel channel, BigDecimal amount, String merchant) {
        super(channel);
        this.amount = amount;
        this.merchant = merchant;
    }

    protected String subject() {
        return "Card transaction";
    }

    protected String body() {
        return "Card used: SGD " + amount + " at " + merchant;
    }
}
