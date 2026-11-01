package com.itgreenzone.patterns.behavioral.strategy.before;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

/**
 * BEFORE the Strategy pattern.
 *
 * One class knows the rules of EVERY payment method. Look at what goes wrong:
 *  - Adding PayNow means editing this class (violates the Open/Closed Principle).
 *  - Card, PayPal and GrabPay logic are tangled together, so a change to one
 *    can break the others, and all of them must be re-tested.
 *  - The single "accountRef" parameter means different things per branch,
 *    which is confusing and error-prone.
 */
public class PaymentService {

    public PaymentReceipt checkout(PaymentType type, BigDecimal amount, String accountRef) {
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        switch (type) {
            case CREDIT_CARD -> {
                // accountRef is a card number here...
                if (accountRef == null || !accountRef.matches("\\d{16}")) {
                    throw new IllegalArgumentException("Card number must be 16 digits");
                }
                BigDecimal fee = amount.multiply(new BigDecimal("0.025")).setScale(2, RoundingMode.HALF_UP);
                System.out.println("Charging card ****" + accountRef.substring(12));
                return new PaymentReceipt("CREDIT_CARD", amount, fee, "CC-" + shortId());
            }
            case PAYPAL -> {
                // ...an email address here...
                if (accountRef == null || !accountRef.contains("@")) {
                    throw new IllegalArgumentException("PayPal needs a valid email");
                }
                BigDecimal fee = amount.multiply(new BigDecimal("0.034"))
                        .add(new BigDecimal("0.50")).setScale(2, RoundingMode.HALF_UP);
                System.out.println("Redirecting to PayPal for " + accountRef);
                return new PaymentReceipt("PAYPAL", amount, fee, "PP-" + shortId());
            }
            case GRABPAY -> {
                // ...and a mobile number here. Every new method adds another branch.
                if (accountRef == null || !accountRef.matches("\\+65[89]\\d{7}")) {
                    throw new IllegalArgumentException("GrabPay needs a Singapore mobile number");
                }
                BigDecimal fee = amount.multiply(new BigDecimal("0.015")).setScale(2, RoundingMode.HALF_UP);
                System.out.println("Sending GrabPay request to " + accountRef);
                return new PaymentReceipt("GRABPAY", amount, fee, "GP-" + shortId());
            }
            default -> throw new IllegalArgumentException("Unsupported payment type: " + type);
        }
    }

    private static String shortId() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
