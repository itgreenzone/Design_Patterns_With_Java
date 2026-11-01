package com.itgreenzone.patterns.structural.bridge.after;

import com.itgreenzone.patterns.structural.bridge.Customer;

import java.math.BigDecimal;
import java.util.List;

/** Any alert type on any channel, chosen at runtime (e.g. from customer preferences). */
public class AfterDemo {

    public static void main(String[] args) {
        Customer alice = new Customer("Alice", "+6591234567", "alice@example.com", "device-abc");
        for (NotificationChannel ch : List.of(new SmsChannel(), new EmailChannel(), new PushChannel())) {
            System.out.println(new FraudAlert(ch, new BigDecimal("999.00"), "ONLINE-XYZ").send(alice));
        }
    }
}
