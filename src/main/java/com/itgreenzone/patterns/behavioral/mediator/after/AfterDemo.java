package com.itgreenzone.patterns.behavioral.mediator.after;

import java.util.ArrayList;
import java.util.List;

/** SQ322 is delayed 90 minutes: one event, a coordinated reaction. */
public class AfterDemo {

    public static void main(String[] args) {
        List<String> log = new ArrayList<>();
        Gate gate = new Gate(log);
        GroundCrew crew = new GroundCrew(log);
        Catering catering = new Catering(log);
        PassengerNotifier pax = new PassengerNotifier(log);
        Flight sq322 = new Flight("SQ322", log);
        TurnaroundCoordinator ops = new TurnaroundCoordinator(gate, crew, catering, pax);
        ops.register(gate, crew, catering, pax, sq322);

        sq322.delay(90);
        log.forEach(System.out::println);
    }
}
