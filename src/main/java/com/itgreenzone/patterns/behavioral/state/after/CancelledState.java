package com.itgreenzone.patterns.behavioral.state.after;

/** CONCRETE STATE: terminal. Every event is illegal. */
public final class CancelledState implements OrderState {

    @Override
    public String name() {
        return "CANCELLED";
    }
}
