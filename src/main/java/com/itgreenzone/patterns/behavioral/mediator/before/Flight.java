package com.itgreenzone.patterns.behavioral.mediator.before;

import java.util.List;

/**
 * BEFORE: every component holds references to the others and calls them directly.
 * The Flight must know Gate, GroundCrew, Catering AND PassengerNotifier, and the
 * rules ("delay over 60 min → change gate") are spread across the classes.
 * With 5 components that's up to 5 × 4 = 20 links to keep consistent.
 */
public class Flight {

    private final String number;
    private final Gate gate;
    private final GroundCrew crew;
    private final Catering catering;
    private final List<String> log;

    public Flight(String number, Gate gate, GroundCrew crew, Catering catering, List<String> log) {
        this.number = number;
        this.gate = gate;
        this.crew = crew;
        this.catering = catering;
        this.log = log;
    }

    public void delay(int minutes) {
        crew.reschedule(number, minutes);
        catering.shift(number, minutes);
        log.add("PAX: " + number + " delayed " + minutes + " min");
        if (minutes > 60) {
            gate.reassign(number, "C" + (minutes / 10));       // Flight knows the gate rule too
        }
    }
}
