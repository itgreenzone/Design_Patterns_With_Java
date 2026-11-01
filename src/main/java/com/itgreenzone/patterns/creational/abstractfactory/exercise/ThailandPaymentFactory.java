package com.itgreenzone.patterns.creational.abstractfactory.exercise;

import com.itgreenzone.patterns.creational.abstractfactory.after.*;

/**
 * HANDS-ON EXERCISE (≈25 minutes) – add the THAILAND family.
 *
 *  - Gateway: "PromptPay", references "PROMPTPAY-1", "PROMPTPAY-2", …
 *  - Tax: VAT 7%
 *  - Receipt: "THB 107.00 (incl. VAT 7.00) ref PROMPTPAY-1"
 *
 * Rules: do NOT change CheckoutService or the SG/MY factories.
 * Test it with: new CheckoutService(new ThailandPaymentFactory()).checkout(new BigDecimal("100.00"))
 * Reference solution: ..abstractfactory.solution.ThailandPaymentFactory
 */
public class ThailandPaymentFactory implements RegionalPaymentFactory {

    public PaymentGateway createGateway() {
        throw new UnsupportedOperationException("TODO");
    }

    public TaxCalculator createTaxCalculator() {
        throw new UnsupportedOperationException("TODO");
    }

    public ReceiptFormatter createReceiptFormatter() {
        throw new UnsupportedOperationException("TODO");
    }
}
