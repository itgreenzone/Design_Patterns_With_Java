package com.itgreenzone.patterns.capstone.processor;

/** Thrown when a payment is refused (for example by the fraud check). */
public class PaymentDeclinedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PaymentDeclinedException(String message) {
        super(message);
    }
}
