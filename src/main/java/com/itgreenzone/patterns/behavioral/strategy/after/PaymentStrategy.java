package com.itgreenzone.patterns.behavioral.strategy.after;

import java.math.BigDecimal;

/**
 * STRATEGY interface.
 *
 * The one contract every payment method must honour. The Context (PaymentService)
 * depends only on this interface, never on a concrete class – that is what lets us
 * add new payment methods without touching the Context.
 */
public interface PaymentStrategy {

    /** Human-readable name, e.g. "CREDIT_CARD". */
    String name();

    /** Charge the given amount and return a receipt. */
    PaymentReceipt pay(BigDecimal amount);
}
