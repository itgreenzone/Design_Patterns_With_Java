package com.itgreenzone.patterns.foundations.uml;

/**
 * A seat on ONE flight. Created by Flight (composition): it cannot exist without it.
 * UML: book() is package-private (~), so only classes in this package can book a seat.
 */
public class Seat {

    private final String code;
    private final Cabin cabin;
    private boolean booked;

    Seat(String code, Cabin cabin) {
        this.code = code;
        this.cabin = cabin;
    }

    void book() {                       // ~ book()
        if (booked) {
            throw new IllegalStateException("Seat " + code + " is already booked");
        }
        booked = true;
    }

    public boolean isBooked() {
        return booked;
    }

    public String code() {
        return code;
    }

    public Cabin cabin() {
        return cabin;
    }
}
