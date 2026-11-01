package com.itgreenzone.patterns.structural.decorator.after;

import com.itgreenzone.patterns.structural.decorator.GatewayTimeoutException;
import com.itgreenzone.patterns.structural.decorator.PaymentRequest;
import com.itgreenzone.patterns.structural.decorator.PaymentResult;

/** CONCRETE DECORATOR: retries on timeout, up to maxAttempts in total. */
public class RetryDecorator extends PaymentProcessorDecorator {

    private final int maxAttempts;

    public RetryDecorator(PaymentProcessor inner, int maxAttempts) {
        super(inner);
        if (maxAttempts < 1) {
            throw new IllegalArgumentException("maxAttempts must be >= 1");
        }
        this.maxAttempts = maxAttempts;
    }

    @Override
    public PaymentResult process(PaymentRequest r) {
        for (int attempt = 1; ; attempt++) {
            try {
                return inner.process(r);
            } catch (GatewayTimeoutException e) {
                if (attempt >= maxAttempts) {
                    return PaymentResult.declined("Gateway unavailable after " + maxAttempts + " attempts");
                }
            }
        }
    }
}
