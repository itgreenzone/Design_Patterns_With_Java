package com.itgreenzone.patterns.structural.bridge.after;

import com.itgreenzone.patterns.structural.bridge.Customer;
import com.itgreenzone.patterns.structural.bridge.Delivery;

/** CONCRETE IMPLEMENTOR: SMS has no subject and a 160-character limit. */
public class SmsChannel implements NotificationChannel {

    public Delivery deliver(Customer c, String subject, String body) {
        String text = body.length() > 160 ? body.substring(0, 157) + "..." : body;
        return new Delivery("SMS", c.mobile(), text);
    }
}
