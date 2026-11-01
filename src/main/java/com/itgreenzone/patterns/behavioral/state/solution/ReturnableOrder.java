package com.itgreenzone.patterns.behavioral.state.solution;

import com.itgreenzone.patterns.behavioral.state.after.DeliveredState;
import com.itgreenzone.patterns.behavioral.state.after.PaymentOrder;
import com.itgreenzone.patterns.behavioral.state.exercise.ReturnedState;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Reference solution: the existing states are untouched. */
public class ReturnableOrder extends PaymentOrder {

    public static final int RETURN_DAYS = 14;

    public ReturnableOrder(String id, BigDecimal amount) {
        super(id, amount);
    }

    public void requestReturn(LocalDate today) {
        if (!(state() instanceof DeliveredState delivered)) {
            throw new IllegalStateException("Cannot return an order that is " + status());
        }
        if (today.isAfter(delivered.deliveredOn().plusDays(RETURN_DAYS))) {
            throw new IllegalStateException("Return window closed");
        }
        refundInFull();
        transitionTo(new ReturnedState());
    }
}
