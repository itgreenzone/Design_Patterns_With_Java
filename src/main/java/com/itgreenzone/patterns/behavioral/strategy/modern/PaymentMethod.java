package com.itgreenzone.patterns.behavioral.strategy.modern;

import java.util.Objects;

/**
 * A payment method = a name + the fee strategy it uses.
 * A record is perfect here: immutable data with no boilerplate.
 */
public record PaymentMethod(String name, FeeStrategy fee) {

    public PaymentMethod {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(fee, "fee");
    }

    public static final PaymentMethod CREDIT_CARD = new PaymentMethod("CREDIT_CARD", FeeStrategy.percent("0.025"));
    public static final PaymentMethod PAYPAL      = new PaymentMethod("PAYPAL", FeeStrategy.percent("0.034").plus("0.50"));
    public static final PaymentMethod GRABPAY     = new PaymentMethod("GRABPAY", FeeStrategy.percent("0.015"));
    public static final PaymentMethod PAYNOW      = new PaymentMethod("PAYNOW", FeeStrategy.free());
}
