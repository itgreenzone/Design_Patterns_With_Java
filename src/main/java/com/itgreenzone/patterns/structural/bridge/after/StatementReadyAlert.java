package com.itgreenzone.patterns.structural.bridge.after;

/** REFINED ABSTRACTION. */
public class StatementReadyAlert extends Alert {

    private final String month;

    public StatementReadyAlert(NotificationChannel channel, String month) {
        super(channel);
        this.month = month;
    }

    protected String subject() {
        return "Statement";
    }

    protected String body() {
        return "Your " + month + " statement is ready";
    }
}
