package com.itgreenzone.patterns.behavioral.state.after;

/** CONCRETE STATE: PAID → SHIPPED, or PAID → CANCELLED with a full refund. */
public final class PaidState implements OrderState {

    @Override
    public String name() {
        return "PAID";
    }

    @Override
    public void ship(PaymentOrder order) {
        order.transitionTo(new ShippedState());
    }

    @Override
    public void cancel(PaymentOrder order) {
        order.refundInFull();
        order.transitionTo(new CancelledState());
    }
}
