package com.itgreenzone.patterns.behavioral.mediator.after;

import java.util.List;

/** COLLEAGUE: reports its delay to the mediator. It has no idea who reacts. */
public class Flight extends Colleague {

    private final String number;

    public Flight(String number, List<String> log) {
        super(log);
        this.number = number;
    }

    public void delay(int minutes) {
        log.add("FLIGHT: " + number + " delayed " + minutes + " min");
        control.flightDelayed(number, minutes);
    }

    public String number() {
        return number;
    }
}
