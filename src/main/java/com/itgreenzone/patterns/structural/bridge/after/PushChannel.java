package com.itgreenzone.patterns.structural.bridge.after;

import com.itgreenzone.patterns.structural.bridge.Customer;
import com.itgreenzone.patterns.structural.bridge.Delivery;

/** CONCRETE IMPLEMENTOR: push = title + short body, sent to the device token. */
public class PushChannel implements NotificationChannel {

    public Delivery deliver(Customer c, String subject, String body) {
        return new Delivery("PUSH", c.deviceToken(), subject + ": " + body);
    }
}
