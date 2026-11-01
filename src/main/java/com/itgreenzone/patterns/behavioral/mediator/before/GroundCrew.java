package com.itgreenzone.patterns.behavioral.mediator.before;

import java.util.List;

public class GroundCrew {

    private final List<String> log;

    public GroundCrew(List<String> log) {
        this.log = log;
    }

    public void reschedule(String flight, int minutes) {
        log.add("CREW: " + flight + " +" + minutes + " min");
    }

    public void moveTo(String flight, String gate) {
        log.add("CREW: " + flight + " moves to " + gate);
    }
}
