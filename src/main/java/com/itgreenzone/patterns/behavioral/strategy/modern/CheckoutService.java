package com.itgreenzone.patterns.behavioral.strategy.modern;

import java.math.BigDecimal;

/**
 * CONTEXT (modern variant).
 * Same idea as before: no if-else on the payment type, just delegate to the
 * strategy the caller passes in.
 *
 * Why not a sealed interface + switch expression here? A sealed hierarchy is
 * deliberately CLOSED: adding PayNow would mean editing the "permits" list and
 * every switch. That fights the Open/Closed goal of Strategy, so we use
 * lambdas instead. (Sealed types shine when the set of cases is fixed.)
 */
public class CheckoutService {

    public Receipt checkout(BigDecimal amount, PaymentMethod method) {
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        return new Receipt(method.name(), amount, method.fee().feeFor(amount));
    }
}
