package com.itgreenzone.patterns.structural.bridge.after;

import com.itgreenzone.patterns.structural.bridge.Customer;
import com.itgreenzone.patterns.structural.bridge.Delivery;

import java.util.Objects;

/**
 * ABSTRACTION: WHAT we want to tell the customer.
 * It holds a reference to a NotificationChannel – that reference is "the bridge".
 * Alert types and channels now vary INDEPENDENTLY: 3 + 3 classes, not 3 × 3.
 */
public abstract class Alert {

    protected final NotificationChannel channel;        // the bridge

    protected Alert(NotificationChannel channel) {
        this.channel = Objects.requireNonNull(channel);
    }

    public final Delivery send(Customer customer) {
        return channel.deliver(customer, subject(), body());
    }

    protected abstract String subject();

    protected abstract String body();
}
