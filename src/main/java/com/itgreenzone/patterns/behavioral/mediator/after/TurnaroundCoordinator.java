package com.itgreenzone.patterns.behavioral.mediator.after;

/**
 * CONCRETE MEDIATOR: the ONE place that knows who reacts to what, and in which
 * order. Colleagues only know this object. Rules like "a delay over 60 minutes
 * moves the flight to another gate" live here, not inside Flight or Gate.
 */
public class TurnaroundCoordinator implements OperationsControl {

    protected final Gate gate;
    protected final GroundCrew crew;
    protected final Catering catering;
    protected final PassengerNotifier passengers;

    public TurnaroundCoordinator(Gate gate, GroundCrew crew, Catering catering, PassengerNotifier passengers) {
        this.gate = gate;
        this.crew = crew;
        this.catering = catering;
        this.passengers = passengers;
    }

    /** Wires colleagues to this mediator. */
    public void register(Colleague... colleagues) {
        for (Colleague c : colleagues) {
            c.setControl(this);
        }
    }

    @Override
    public void flightDelayed(String flight, int minutes) {
        crew.reschedule(flight, minutes);
        catering.shift(flight, minutes);
        passengers.announce(flight + " delayed " + minutes + " min");
        if (minutes > 60) {
            gate.reassign(flight, "C" + (minutes / 10));       // which triggers gateChanged(...)
        }
    }

    @Override
    public void gateChanged(String flight, String newGate) {
        crew.moveTo(flight, newGate);
        passengers.announce(flight + " now boards at " + newGate);
    }
}
