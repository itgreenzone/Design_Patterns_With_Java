package com.itgreenzone.patterns.behavioral.state.after;

/** CONCRETE STATE: NEW → PAID, or NEW → CANCELLED (nothing to refund). */
public final class NewState implements OrderState {

    @Override
    public String name() {
        return "NEW";
    }

    @Override
    public void pay(PaymentOrder order) {
        order.transitionTo(new PaidState());
    }

    @Override
    public void cancel(PaymentOrder order) {
        order.transitionTo(new CancelledState());
    }
}
