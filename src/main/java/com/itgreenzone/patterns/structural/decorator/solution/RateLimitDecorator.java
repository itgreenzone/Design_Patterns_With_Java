package com.itgreenzone.patterns.structural.decorator.solution;

import com.itgreenzone.patterns.structural.decorator.PaymentRequest;
import com.itgreenzone.patterns.structural.decorator.PaymentResult;
import com.itgreenzone.patterns.structural.decorator.after.PaymentProcessor;
import com.itgreenzone.patterns.structural.decorator.after.PaymentProcessorDecorator;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/** Reference solution: counts per merchant atomically; short-circuits when over the limit. */
public class RateLimitDecorator extends PaymentProcessorDecorator {

    private final int maxPerMerchant;
    private final ConcurrentMap<String, Integer> counts = new ConcurrentHashMap<>();

    public RateLimitDecorator(PaymentProcessor inner, int maxPerMerchant) {
        super(inner);
        this.maxPerMerchant = maxPerMerchant;
    }

    @Override
    public PaymentResult process(PaymentRequest request) {
        int count = counts.merge(request.merchantId(), 1, Integer::sum);
        if (count > maxPerMerchant) {
            return PaymentResult.declined("Rate limit exceeded");
        }
        return inner.process(request);
    }
}
