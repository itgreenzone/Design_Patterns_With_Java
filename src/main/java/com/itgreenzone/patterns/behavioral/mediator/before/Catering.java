package com.itgreenzone.patterns.behavioral.mediator.before;

import java.util.List;

public class Catering {

    private final List<String> log;

    public Catering(List<String> log) {
        this.log = log;
    }

    public void shift(String flight, int minutes) {
        log.add("CATERING: " + flight + " +" + minutes + " min");
    }
}
