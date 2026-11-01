package com.itgreenzone.patterns.creational.abstractfactory.after;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

/** CONCRETE FACTORY: the Malaysia family – DuitNow, SST 8%, RM receipts. */
public class MalaysiaPaymentFactory implements RegionalPaymentFactory {

    public PaymentGateway createGateway() {
        return new PaymentGateway() {
            private final AtomicInteger seq = new AtomicInteger();

            public String name() {
                return "DuitNow";
            }

            public String collect(BigDecimal amount) {
                return "DUITNOW-" + seq.incrementAndGet();
            }
        };
    }

    public TaxCalculator createTaxCalculator() {
        return new PercentageTax("SST", new BigDecimal("0.08"));
    }

    public ReceiptFormatter createReceiptFormatter() {
        return (r, taxName) -> "RM " + r.total() + " (incl. " + taxName + " " + r.tax() + ") ref " + r.paymentRef();
    }
}
