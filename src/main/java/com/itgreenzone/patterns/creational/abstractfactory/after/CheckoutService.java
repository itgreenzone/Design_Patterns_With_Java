package com.itgreenzone.patterns.creational.abstractfactory.after;

import com.itgreenzone.patterns.creational.abstractfactory.Receipt;

import java.math.BigDecimal;

/**
 * CLIENT: knows only the interfaces. It gets ALL its components from ONE factory,
 * so a Singapore gateway can never be paired with Malaysian tax.
 */
public class CheckoutService {

    private final PaymentGateway gateway;
    private final TaxCalculator tax;
    private final ReceiptFormatter formatter;

    public CheckoutService(RegionalPaymentFactory factory) {
        this.gateway = factory.createGateway();
        this.tax = factory.createTaxCalculator();
        this.formatter = factory.createReceiptFormatter();
    }

    public String checkout(BigDecimal net) {
        BigDecimal taxAmount = tax.taxOn(net);
        BigDecimal total = net.add(taxAmount);
        String ref = gateway.collect(total);
        return formatter.format(new Receipt(net, taxAmount, total, ref), tax.taxName());
    }

    public String gatewayName() {
        return gateway.name();
    }
}
