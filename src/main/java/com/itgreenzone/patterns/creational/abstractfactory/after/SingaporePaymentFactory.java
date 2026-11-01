package com.itgreenzone.patterns.creational.abstractfactory.after;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

/** CONCRETE FACTORY: the Singapore family – PayNow, GST 9%, SGD receipts. */
public class SingaporePaymentFactory implements RegionalPaymentFactory {

    public PaymentGateway createGateway() {
        return new PaymentGateway() {
            private final AtomicInteger seq = new AtomicInteger();

            public String name() {
                return "PayNow";
            }

            public String collect(BigDecimal amount) {
                return "PAYNOW-" + seq.incrementAndGet();
            }
        };
    }

    public TaxCalculator createTaxCalculator() {
        return new PercentageTax("GST", new BigDecimal("0.09"));
    }

    public ReceiptFormatter createReceiptFormatter() {
        return (r, taxName) -> "SGD " + r.total() + " (incl. " + taxName + " " + r.tax() + ") ref " + r.paymentRef();
    }
}
