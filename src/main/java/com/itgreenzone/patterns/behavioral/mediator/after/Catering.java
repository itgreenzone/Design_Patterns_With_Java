package com.itgreenzone.patterns.behavioral.mediator.after;

import java.util.List;

/** COLLEAGUE. */
public class Catering extends Colleague {

    public Catering(List<String> log) {
        super(log);
    }

    public void shift(String flight, int minutes) {
        log.add("CATERING: " + flight + " +" + minutes + " min");
    }
}
