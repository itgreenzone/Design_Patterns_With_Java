package com.itgreenzone.patterns.capstone.processor;

import java.math.BigDecimal;
import java.util.List;

/** DECORATOR (L12): logs every call, then delegates. */
public class LoggingProcessor implements PaymentProcessor {

    private final PaymentProcessor inner;
    private final List<String> log;

    public LoggingProcessor(PaymentProcessor inner, List<String> log) {
        this.inner = inner;
        this.log = log;
    }

    @Override
    public String charge(String orderId, BigDecimal amount) {
        log.add("charge " + orderId + " " + amount);
        String reference = inner.charge(orderId, amount);
        log.add("charged " + orderId + " ref " + reference);
        return reference;
    }

    @Override
    public void refund(String reference, BigDecimal amount) {
        log.add("refund " + reference + " " + amount);
        inner.refund(reference, amount);
    }
}
