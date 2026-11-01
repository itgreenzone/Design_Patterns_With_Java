package com.itgreenzone.patterns.structural.bridge.solution;

import com.itgreenzone.patterns.structural.bridge.after.Alert;
import com.itgreenzone.patterns.structural.bridge.after.NotificationChannel;

import java.math.BigDecimal;

/** Reference solution: one new refined abstraction. */
public class LowBalanceAlert extends Alert {

    private final BigDecimal balance;
    private final BigDecimal threshold;

    public LowBalanceAlert(NotificationChannel channel, BigDecimal balance, BigDecimal threshold) {
        super(channel);
        this.balance = balance;
        this.threshold = threshold;
    }

    protected String subject() {
        return "Low balance";
    }

    protected String body() {
        return "Balance SGD " + balance + " is below SGD " + threshold;
    }
}
