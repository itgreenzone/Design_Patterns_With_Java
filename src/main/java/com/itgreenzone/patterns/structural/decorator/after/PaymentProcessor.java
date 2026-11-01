package com.itgreenzone.patterns.structural.decorator.after;

import com.itgreenzone.patterns.structural.decorator.PaymentRequest;
import com.itgreenzone.patterns.structural.decorator.PaymentResult;

/** COMPONENT: the one interface the gateway AND every decorator implement. */
@FunctionalInterface
public interface PaymentProcessor {

    PaymentResult process(PaymentRequest request);
}
