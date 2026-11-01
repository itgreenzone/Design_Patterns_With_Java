package com.itgreenzone.patterns.creational.abstractfactory.solution;

import com.itgreenzone.patterns.creational.abstractfactory.after.PaymentGateway;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

/** Reference solution: Thailand's concrete gateway. */
public class PromptPayGateway implements PaymentGateway {

    private final AtomicInteger seq = new AtomicInteger();

    public String name() {
        return "PromptPay";
    }

    public String collect(BigDecimal amount) {
        return "PROMPTPAY-" + seq.incrementAndGet();
    }
}
