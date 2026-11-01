package com.itgreenzone.patterns.behavioral.mediator.after;

import java.util.List;

/** COLLEAGUE: performs a gate change, then tells the mediator about it. */
public class Gate extends Colleague {

    public Gate(List<String> log) {
        super(log);
    }

    public void reassign(String flight, String newGate) {
        log.add("GATE: " + flight + " -> " + newGate);
        control.gateChanged(flight, newGate);
    }
}
