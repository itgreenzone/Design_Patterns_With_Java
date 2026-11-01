package com.itgreenzone.patterns.behavioral.mediator.after;

/**
 * MEDIATOR: colleagues report events HERE instead of calling each other.
 */
public interface OperationsControl {

    void flightDelayed(String flight, int minutes);

    void gateChanged(String flight, String newGate);
}
