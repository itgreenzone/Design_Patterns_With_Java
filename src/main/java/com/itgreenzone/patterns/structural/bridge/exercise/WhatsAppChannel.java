package com.itgreenzone.patterns.structural.bridge.exercise;

import com.itgreenzone.patterns.structural.bridge.Customer;
import com.itgreenzone.patterns.structural.bridge.Delivery;
import com.itgreenzone.patterns.structural.bridge.after.NotificationChannel;

/**
 * HANDS-ON EXERCISE (≈20 minutes) – part 1: a new CHANNEL.
 * Deliver to the customer's mobile with channel "WHATSAPP" and text "*subject*\nbody"
 * (WhatsApp uses *asterisks* for bold). No change to any Alert class!
 */
public class WhatsAppChannel implements NotificationChannel {

    public Delivery deliver(Customer customer, String subject, String body) {
        throw new UnsupportedOperationException("TODO");
    }
}
