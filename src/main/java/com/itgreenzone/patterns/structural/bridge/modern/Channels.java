package com.itgreenzone.patterns.structural.bridge.modern;

import com.itgreenzone.patterns.structural.bridge.Delivery;
import com.itgreenzone.patterns.structural.bridge.after.NotificationChannel;

/**
 * MODERN VARIANT: NotificationChannel has ONE method, so a channel can be a lambda.
 * Handy for quick channels and test doubles; real channels with HTTP clients,
 * retries and templates still deserve their own class.
 */
public final class Channels {

    private Channels() {
    }

    public static NotificationChannel inApp() {
        return (customer, subject, body) -> new Delivery("IN_APP", customer.name(), "[" + subject + "] " + body);
    }

    public static NotificationChannel upperCase(NotificationChannel inner) {
        return (customer, subject, body) -> inner.deliver(customer, subject.toUpperCase(), body.toUpperCase());
    }
}
