package com.itgreenzone.patterns.structural.flyweight.before;

import java.util.List;

/**
 * BEFORE: every seat object carries ALL the heavy cabin data – including a 2 KB
 * seat icon – even though 459 seats on an A380 share only 4 cabin products.
 */
public class Seat {

    private final int row;
    private final char letter;
    private boolean occupied;
    private final String cabin;
    private final int pitchInches;
    private final int reclineDegrees;
    private final List<String> amenities;
    private final byte[] icon;                 // heavy: 2 KB per seat!

    public Seat(int row, char letter, String cabin, int pitchInches, int reclineDegrees, List<String> amenities) {
        this.row = row;
        this.letter = letter;
        this.cabin = cabin;
        this.pitchInches = pitchInches;
        this.reclineDegrees = reclineDegrees;
        this.amenities = List.copyOf(amenities);
        this.icon = new byte[2048];            // loaded from the CDN, once PER SEAT
    }

    public String render() {
        return row + "" + letter + " " + cabin + " " + pitchInches + "\" " + (occupied ? "X" : "_");
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public byte[] icon() {
        return icon;
    }

    public String cabin() {
        return cabin;
    }
}
