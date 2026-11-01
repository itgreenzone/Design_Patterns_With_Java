package com.itgreenzone.patterns.behavioral.state.exercise;

import com.itgreenzone.patterns.behavioral.state.after.OrderState;

/** New terminal state for the exercise. Already done for you. */
public final class ReturnedState implements OrderState {

    @Override
    public String name() {
        return "RETURNED";
    }
}
