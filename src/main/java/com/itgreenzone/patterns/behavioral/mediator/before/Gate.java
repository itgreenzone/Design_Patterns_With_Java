package com.itgreenzone.patterns.behavioral.mediator.before;

import java.util.List;

/** BEFORE: the gate also calls the crew and the passengers directly. */
public class Gate {

    private final GroundCrew crew;
    private final List<String> log;

    public Gate(GroundCrew crew, List<String> log) {
        this.crew = crew;
        this.log = log;
    }

    public void reassign(String flight, String newGate) {
        log.add("GATE: " + flight + " -> " + newGate);
        crew.moveTo(flight, newGate);
        log.add("PAX: " + flight + " now boards at " + newGate);
    }
}
