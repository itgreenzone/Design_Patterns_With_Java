package com.itgreenzone.patterns.structural.decorator.after;

import com.itgreenzone.patterns.structural.decorator.PaymentRequest;
import com.itgreenzone.patterns.structural.decorator.PaymentResult;

import java.util.Objects;

/**
 * DECORATOR base: IS-A PaymentProcessor and HAS-A PaymentProcessor.
 * By default it just passes the call through; subclasses add behaviour
 * before and/or after delegating.
 */
public abstract class PaymentProcessorDecorator implements PaymentProcessor {

    protected final PaymentProcessor inner;

    protected PaymentProcessorDecorator(PaymentProcessor inner) {
        this.inner = Objects.requireNonNull(inner);
    }

    @Override
    public PaymentResult process(PaymentRequest request) {
        return inner.process(request);
    }
}
