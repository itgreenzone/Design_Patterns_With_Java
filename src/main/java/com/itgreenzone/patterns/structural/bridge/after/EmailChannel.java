package com.itgreenzone.patterns.structural.bridge.after;

import com.itgreenzone.patterns.structural.bridge.Customer;
import com.itgreenzone.patterns.structural.bridge.Delivery;

/** CONCRETE IMPLEMENTOR: email has a subject line. */
public class EmailChannel implements NotificationChannel {

    public Delivery deliver(Customer c, String subject, String body) {
        return new Delivery("EMAIL", c.email(), subject + " | " + body);
    }
}
