package com.itgreenzone.patterns.behavioral.strategy.after;

import java.math.BigDecimal;

/**
 * CONCRETE STRATEGY added LATER: PayNow (no fee).
 *
 * This is the Open/Closed Principle in action: we added a whole new payment
 * method by writing ONE new class. PaymentService was not edited, and the
 * existing strategies and their tests were not touched.
 */
public class PayNowPayment implements PaymentStrategy {

    private final String payNowId;

    public PayNowPayment(String payNowId) {
        if (payNowId == null || payNowId.isBlank()) {
            throw new IllegalArgumentException("PayNow needs a mobile number or UEN");
        }
        this.payNowId = payNowId;
    }

    @Override
    public String name() {
        return "PAYNOW";
    }

    @Override
    public PaymentReceipt pay(BigDecimal amount) {
        System.out.println("Showing PayNow QR for " + payNowId);
        return new PaymentReceipt(name(), amount, BigDecimal.ZERO.setScale(2), Fees.reference("PN"));
    }
}
