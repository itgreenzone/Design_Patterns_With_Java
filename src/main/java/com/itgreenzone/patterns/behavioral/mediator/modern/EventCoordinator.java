package com.itgreenzone.patterns.behavioral.mediator.modern;

import java.util.ArrayList;
import java.util.List;

/**
 * MODERN VARIANT: colleagues publish event records to ONE method; an exhaustive
 * switch decides who reacts. Reactions are listed in one readable place.
 * (Spring's ApplicationEventPublisher can play the same hub role across beans.)
 */
public class EventCoordinator {

    private final List<String> actions = new ArrayList<>();

    public void publish(OpsEvent event) {
        switch (event) {
            case OpsEvent.FlightDelayed d -> {
                actions.add("CREW +" + d.minutes());
                actions.add("CATERING +" + d.minutes());
                actions.add("PAX delayed " + d.minutes());
                if (d.minutes() > 60) {
                    publish(new OpsEvent.GateChanged(d.flight(), "C" + d.minutes() / 10));
                }
            }
            case OpsEvent.GateChanged g -> {
                actions.add("CREW -> " + g.newGate());
                actions.add("PAX -> " + g.newGate());
            }
        }
    }

    public List<String> actions() {
        return List.copyOf(actions);
    }
}
