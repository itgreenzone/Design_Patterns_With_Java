package com.itgreenzone.patterns.behavioral.mediator.exercise;

import java.util.List;

/** NEW COLLEAGUE: reports refuelling delays to the mediator. Already done for you. */
public class FuelTruck {

    private final List<String> log;
    private FuelAwareControl control;

    public FuelTruck(List<String> log) {
        this.log = log;
    }

    public void setControl(FuelAwareControl control) {
        this.control = control;
    }

    public void reportDelay(String flight, int minutes) {
        log.add("FUEL: " + flight + " refuelling late by " + minutes + " min");
        control.fuelDelayed(flight, minutes);
    }
}
