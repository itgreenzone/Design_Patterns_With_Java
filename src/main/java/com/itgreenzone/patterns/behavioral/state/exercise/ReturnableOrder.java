package com.itgreenzone.patterns.behavioral.state.exercise;

import com.itgreenzone.patterns.behavioral.state.after.PaymentOrder;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * HANDS-ON EXERCISE (≈20 minutes)
 *
 * Add a RETURNED state, reachable only from DELIVERED within 14 days:
 *   - requestReturn(today): if the current state is a DeliveredState and today is
 *     no more than 14 days after deliveredOn(), refund in full and move to
 *     ReturnedState.
 *   - otherwise throw IllegalStateException
 *     ("Cannot return an order that is <STATUS>" or "Return window closed").
 * Hint: use state(), refundInFull() and transitionTo(...) from PaymentOrder.
 * Reference: ..state.solution.ReturnableOrder
 */
public class ReturnableOrder extends PaymentOrder {

    public static final int RETURN_DAYS = 14;

    public ReturnableOrder(String id, BigDecimal amount) {
        super(id, amount);
    }

    public void requestReturn(LocalDate today) {
        throw new UnsupportedOperationException("TODO");
    }
}
