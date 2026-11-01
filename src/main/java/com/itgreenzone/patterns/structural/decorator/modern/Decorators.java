package com.itgreenzone.patterns.structural.decorator.modern;

import com.itgreenzone.patterns.structural.decorator.PaymentResult;
import com.itgreenzone.patterns.structural.decorator.after.PaymentProcessor;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.UnaryOperator;

/**
 * MODERN VARIANT: because PaymentProcessor is a functional interface, a decorator
 * is just a function PaymentProcessor -> PaymentProcessor, and stacks are built by
 * composing functions. Good for small, stateless wrappers.
 */
public final class Decorators {

    private Decorators() {
    }

    public static UnaryOperator<PaymentProcessor> logging(List<String> log) {
        return inner -> request -> {
            log.add("-> " + request.amount());
            PaymentResult r = inner.process(request);
            log.add("<- " + r.approved());
            return r;
        };
    }

    public static UnaryOperator<PaymentProcessor> limit(BigDecimal max) {
        return inner -> request -> request.amount().compareTo(max) > 0
                ? PaymentResult.declined("Over limit " + max)
                : inner.process(request);
    }

    /** Applies wrappers so the FIRST in the list ends up OUTERMOST. */
    public static PaymentProcessor stack(PaymentProcessor core, List<UnaryOperator<PaymentProcessor>> outermostFirst) {
        PaymentProcessor p = core;
        for (UnaryOperator<PaymentProcessor> wrap : outermostFirst.reversed()) {
            p = wrap.apply(p);
        }
        return p;
    }
}
