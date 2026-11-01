package com.itgreenzone.patterns.behavioral.strategy.after;

import java.math.BigDecimal;

/**
 * CONCRETE STRATEGY: GrabPay wallet (1.5% fee, Singapore mobile number).
 */
public class GrabPayPayment implements PaymentStrategy {

    private final String mobile;

    public GrabPayPayment(String mobile) {
        if (mobile == null || !mobile.matches("\\+65[89]\\d{7}")) {
            throw new IllegalArgumentException("GrabPay needs a Singapore mobile number");
        }
        this.mobile = mobile;
    }

    @Override
    public String name() {
        return "GRABPAY";
    }

    @Override
    public PaymentReceipt pay(BigDecimal amount) {
        BigDecimal fee = Fees.percent(amount, "0.015");
        System.out.println("Sending GrabPay request to " + mobile);
        return new PaymentReceipt(name(), amount, fee, Fees.reference("GP"));
    }
}
