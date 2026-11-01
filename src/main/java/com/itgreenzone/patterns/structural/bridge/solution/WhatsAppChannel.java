package com.itgreenzone.patterns.structural.bridge.solution;

import com.itgreenzone.patterns.structural.bridge.Customer;
import com.itgreenzone.patterns.structural.bridge.Delivery;
import com.itgreenzone.patterns.structural.bridge.after.NotificationChannel;

/** Reference solution: one new implementor. */
public class WhatsAppChannel implements NotificationChannel {

    public Delivery deliver(Customer c, String subject, String body) {
        return new Delivery("WHATSAPP", c.mobile(), "*" + subject + "*\n" + body);
    }
}
