package com.itgreenzone.patterns.structural.decorator.after;

import com.itgreenzone.patterns.structural.decorator.PaymentRequest;
import com.itgreenzone.patterns.structural.decorator.PaymentResult;

import java.math.BigDecimal;

/**
 * CONCRETE DECORATOR: declines payments above a limit WITHOUT calling the gateway.
 * Note: the limit is in SGD, and it compares the amount as given. That's why the
 * ORDER of wrapping matters (see the tests): convert first, then screen.
 */
public class FraudScreenDecorator extends PaymentProcessorDecorator {

    private final BigDecimal limitSgd;

    public FraudScreenDecorator(PaymentProcessor inner, BigDecimal limitSgd) {
        super(inner);
        this.limitSgd = limitSgd;
    }

    @Override
    public PaymentResult process(PaymentRequest r) {
        if (r.amount().compareTo(limitSgd) > 0) {
            return PaymentResult.declined("Fraud screen: over SGD " + limitSgd);   // short-circuit
        }
        return inner.process(r);
    }
}
