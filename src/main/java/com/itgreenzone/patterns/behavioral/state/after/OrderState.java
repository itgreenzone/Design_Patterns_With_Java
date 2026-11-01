package com.itgreenzone.patterns.behavioral.state.after;

import java.time.LocalDate;

/**
 * STATE: one method per event. Every event is ILLEGAL by default; each concrete
 * state overrides only the transitions it allows, and picks the NEXT state itself.
 */
public interface OrderState {

    String name();

    default void pay(PaymentOrder order) {
        throw illegal("pay");
    }

    default void ship(PaymentOrder order) {
        throw illegal("ship");
    }

    default void deliver(PaymentOrder order, LocalDate on) {
        throw illegal("deliver");
    }

    default void cancel(PaymentOrder order) {
        throw illegal("cancel");
    }

    private IllegalStateException illegal(String action) {
        return new IllegalStateException("Cannot " + action + " an order that is " + name());
    }
}
