package com.itgreenzone.patterns.behavioral.mediator.after;

import java.util.List;

/** COLLEAGUE. */
public class PassengerNotifier extends Colleague {

    public PassengerNotifier(List<String> log) {
        super(log);
    }

    public void announce(String message) {
        log.add("PAX: " + message);
    }
}
