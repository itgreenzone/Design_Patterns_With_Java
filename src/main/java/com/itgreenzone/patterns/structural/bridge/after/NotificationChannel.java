package com.itgreenzone.patterns.structural.bridge.after;

import com.itgreenzone.patterns.structural.bridge.Customer;
import com.itgreenzone.patterns.structural.bridge.Delivery;

/**
 * IMPLEMENTOR: HOW a message gets to the customer.
 * It knows nothing about alert types.
 */
public interface NotificationChannel {

    Delivery deliver(Customer customer, String subject, String body);
}
