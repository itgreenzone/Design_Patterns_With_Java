package com.itgreenzone.patterns.behavioral.mediator.modern;

/** MODERN VARIANT: events as a sealed set of records. */
public sealed interface OpsEvent {

    record FlightDelayed(String flight, int minutes) implements OpsEvent { }

    record GateChanged(String flight, String newGate) implements OpsEvent { }
}
