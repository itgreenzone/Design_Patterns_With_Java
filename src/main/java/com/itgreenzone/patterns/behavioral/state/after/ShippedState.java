package com.itgreenzone.patterns.behavioral.state.after;

import java.time.LocalDate;

/** CONCRETE STATE: SHIPPED → DELIVERED only. cancel() stays illegal (default). */
public final class ShippedState implements OrderState {

    @Override
    public String name() {
        return "SHIPPED";
    }

    @Override
    public void deliver(PaymentOrder order, LocalDate on) {
        order.transitionTo(new DeliveredState(on));
    }
}
