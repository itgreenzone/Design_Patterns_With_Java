package com.itgreenzone.patterns.behavioral.mediator.exercise;

import com.itgreenzone.patterns.behavioral.mediator.after.OperationsControl;

/** The mediator interface, extended with the new event for the exercise. */
public interface FuelAwareControl extends OperationsControl {

    void fuelDelayed(String flight, int minutes);
}
