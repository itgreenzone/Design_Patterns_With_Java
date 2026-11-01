package com.itgreenzone.patterns.capstone.processor;

import java.math.BigDecimal;

/** COMPONENT for the decorators: talks to a payment gateway. */
public interface PaymentProcessor {

    /** Charges the amount and returns the gateway reference. */
    String charge(String orderId, BigDecimal amount);

    void refund(String reference, BigDecimal amount);
}
