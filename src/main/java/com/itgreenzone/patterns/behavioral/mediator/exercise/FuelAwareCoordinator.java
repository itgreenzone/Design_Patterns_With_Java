package com.itgreenzone.patterns.behavioral.mediator.exercise;

import com.itgreenzone.patterns.behavioral.mediator.after.*;

/**
 * HANDS-ON EXERCISE (≈20 minutes)
 *
 * Handle the new "fuel delay" event in the MEDIATOR ONLY:
 *   - a fuel delay of N minutes is treated as a flight delay of N minutes
 *     (reuse flightDelayed so crew, catering, passengers and gate rules all apply)
 *   - additionally announce "<flight> refuelling, please stay near the gate"
 * No other colleague class may change. Reference: ..mediator.solution.FuelAwareCoordinator
 */
public class FuelAwareCoordinator extends TurnaroundCoordinator implements FuelAwareControl {

    public FuelAwareCoordinator(Gate gate, GroundCrew crew, Catering catering, PassengerNotifier passengers) {
        super(gate, crew, catering, passengers);
    }

    @Override
    public void fuelDelayed(String flight, int minutes) {
        throw new UnsupportedOperationException("TODO");
    }
}
