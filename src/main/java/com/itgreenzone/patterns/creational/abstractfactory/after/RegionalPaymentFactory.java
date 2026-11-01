package com.itgreenzone.patterns.creational.abstractfactory.after;

/**
 * ABSTRACT FACTORY: one method per product in the family.
 * A concrete factory always returns products that belong together.
 */
public interface RegionalPaymentFactory {

    PaymentGateway createGateway();

    TaxCalculator createTaxCalculator();

    ReceiptFormatter createReceiptFormatter();
}
