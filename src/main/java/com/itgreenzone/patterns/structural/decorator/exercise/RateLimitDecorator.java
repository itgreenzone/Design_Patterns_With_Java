package com.itgreenzone.patterns.structural.decorator.exercise;

import com.itgreenzone.patterns.structural.decorator.PaymentRequest;
import com.itgreenzone.patterns.structural.decorator.PaymentResult;
import com.itgreenzone.patterns.structural.decorator.after.PaymentProcessor;
import com.itgreenzone.patterns.structural.decorator.after.PaymentProcessorDecorator;

/**
 * HANDS-ON EXERCISE (≈20 minutes)
 *
 * Allow at most maxPerMerchant payments per merchantId. Further payments are
 * DECLINED with reason "Rate limit exceeded" and must NOT reach the inner processor.
 * It must be thread-safe (hint: ConcurrentHashMap + AtomicInteger, or merge()).
 * Then stack it OUTSIDE the retry decorator and explain why that order matters.
 * Reference solution: ..decorator.solution.RateLimitDecorator
 */
public class RateLimitDecorator extends PaymentProcessorDecorator {

    public RateLimitDecorator(PaymentProcessor inner, int maxPerMerchant) {
        super(inner);
    }

    @Override
    public PaymentResult process(PaymentRequest request) {
        throw new UnsupportedOperationException("TODO");
    }
}
