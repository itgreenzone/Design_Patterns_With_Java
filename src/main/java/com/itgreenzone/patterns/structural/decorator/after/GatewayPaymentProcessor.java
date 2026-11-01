package com.itgreenzone.patterns.structural.decorator.after;

import com.itgreenzone.patterns.structural.decorator.GatewayTimeoutException;
import com.itgreenzone.patterns.structural.decorator.PaymentRequest;
import com.itgreenzone.patterns.structural.decorator.PaymentResult;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CONCRETE COMPONENT: the real card gateway. It only accepts SGD, and it can be
 * told to time out on the first N calls so we can demonstrate retries.
 */
public class GatewayPaymentProcessor implements PaymentProcessor {

    private final AtomicInteger calls = new AtomicInteger();
    private final int timeoutsBeforeSuccess;

    public GatewayPaymentProcessor() {
        this(0);
    }

    public GatewayPaymentProcessor(int timeoutsBeforeSuccess) {
        this.timeoutsBeforeSuccess = timeoutsBeforeSuccess;
    }

    @Override
    public PaymentResult process(PaymentRequest r) {
        int n = calls.incrementAndGet();
        if (n <= timeoutsBeforeSuccess) {
            throw new GatewayTimeoutException("Gateway timeout on call " + n);
        }
        if (!"SGD".equals(r.currency())) {
            return PaymentResult.declined("Gateway accepts SGD only");
        }
        return PaymentResult.approved(r.amount(), "GW-" + n);
    }

    public int calls() {
        return calls.get();
    }
}
