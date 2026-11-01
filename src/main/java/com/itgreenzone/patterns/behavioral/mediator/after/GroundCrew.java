package com.itgreenzone.patterns.behavioral.mediator.after;

import java.util.List;

/** COLLEAGUE: only receives instructions (from the mediator). */
public class GroundCrew extends Colleague {

    public GroundCrew(List<String> log) {
        super(log);
    }

    public void reschedule(String flight, int minutes) {
        log.add("CREW: " + flight + " +" + minutes + " min");
    }

    public void moveTo(String flight, String gate) {
        log.add("CREW: " + flight + " moves to " + gate);
    }
}
