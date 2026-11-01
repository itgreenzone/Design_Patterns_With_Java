package com.itgreenzone.patterns.behavioral.strategy.before;

/**
 * The payment methods our checkout supports.
 * Every new value here forces a change inside PaymentService – that is the pain.
 */
public enum PaymentType {
    CREDIT_CARD,
    PAYPAL,
    GRABPAY
}
