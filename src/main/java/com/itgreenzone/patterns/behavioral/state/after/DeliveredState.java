package com.itgreenzone.patterns.behavioral.state.after;

import java.time.LocalDate;
import java.util.Objects;

/** CONCRETE STATE: final for the main flow. Remembers the delivery date. */
public final class DeliveredState implements OrderState {

    private final LocalDate deliveredOn;

    public DeliveredState(LocalDate deliveredOn) {
        this.deliveredOn = Objects.requireNonNull(deliveredOn);
    }

    public LocalDate deliveredOn() {
        return deliveredOn;
    }

    @Override
    public String name() {
        return "DELIVERED";
    }
}
