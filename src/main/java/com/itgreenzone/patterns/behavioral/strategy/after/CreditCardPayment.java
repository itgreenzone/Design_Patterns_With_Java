package com.itgreenzone.patterns.behavioral.strategy.after;

import java.math.BigDecimal;

/**
 * CONCRETE STRATEGY: credit card.
 * Each strategy owns its own data (card number) and its own rules (2.5% fee),
 * so card logic can change without any risk to PayPal or GrabPay.
 */
public class CreditCardPayment implements PaymentStrategy {

    private final String cardNumber;

    public CreditCardPayment(String cardNumber) {
        // Validate once, when the strategy is created, not on every payment.
        if (cardNumber == null || !cardNumber.matches("\\d{16}")) {
            throw new IllegalArgumentException("Card number must be 16 digits");
        }
        this.cardNumber = cardNumber;
    }

    @Override
    public String name() {
        return "CREDIT_CARD";
    }

    @Override
    public PaymentReceipt pay(BigDecimal amount) {
        BigDecimal fee = Fees.percent(amount, "0.025");
        System.out.println("Charging card ****" + cardNumber.substring(12));
        return new PaymentReceipt(name(), amount, fee, Fees.reference("CC"));
    }
}
