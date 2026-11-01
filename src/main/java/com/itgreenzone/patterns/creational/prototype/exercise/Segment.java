package com.itgreenzone.patterns.creational.prototype.exercise;

/** One mutable flight leg (seat can change). */
public class Segment {

    private final String flightNo;
    private String seat;

    public Segment(String flightNo, String seat) {
        this.flightNo = flightNo;
        this.seat = seat;
    }

    public Segment(Segment other) {
        this(other.flightNo, other.seat);
    }

    public String flightNo() {
        return flightNo;
    }

    public String seat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }
}
