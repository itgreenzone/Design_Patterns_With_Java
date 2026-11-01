package com.itgreenzone.patterns.behavioral.mediator.solution;

import com.itgreenzone.patterns.behavioral.mediator.after.*;
import com.itgreenzone.patterns.behavioral.mediator.exercise.FuelAwareControl;

/** Reference solution: the new rule lives in the mediator; colleagues are untouched. */
public class FuelAwareCoordinator extends TurnaroundCoordinator implements FuelAwareControl {

    public FuelAwareCoordinator(Gate gate, GroundCrew crew, Catering catering, PassengerNotifier passengers) {
        super(gate, crew, catering, passengers);
    }

    @Override
    public void fuelDelayed(String flight, int minutes) {
        flightDelayed(flight, minutes);
        passengers.announce(flight + " refuelling, please stay near the gate");
    }
}
