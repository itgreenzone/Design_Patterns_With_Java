package com.itgreenzone.patterns.creational.abstractfactory.solution;

import com.itgreenzone.patterns.creational.abstractfactory.after.*;

import java.math.BigDecimal;

/** Reference solution: one new factory + its products. Nothing else changed. */
public class ThailandPaymentFactory implements RegionalPaymentFactory {

    public PaymentGateway createGateway() {
        return new PromptPayGateway();
    }

    public TaxCalculator createTaxCalculator() {
        return new PercentageTax("VAT", new BigDecimal("0.07"));
    }

    public ReceiptFormatter createReceiptFormatter() {
        return (r, taxName) -> "THB " + r.total() + " (incl. " + taxName + " " + r.tax() + ") ref " + r.paymentRef();
    }
}
