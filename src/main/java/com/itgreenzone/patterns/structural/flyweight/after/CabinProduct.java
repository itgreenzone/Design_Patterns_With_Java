package com.itgreenzone.patterns.structural.flyweight.after;

import java.util.List;

/**
 * FLYWEIGHT: the heavy, SHARED, IMMUTABLE part of a seat (intrinsic state).
 * One instance per cabin product, shared by hundreds of seats. Extrinsic state
 * (row, letter, occupied) is passed IN when needed, never stored here.
 */
public final class CabinProduct {

    private final String cabin;
    private final int pitchInches;
    private final int reclineDegrees;
    private final List<String> amenities;
    private final byte[] icon;

    CabinProduct(String cabin, int pitchInches, int reclineDegrees, List<String> amenities) {   // only the factory creates
        this.cabin = cabin;
        this.pitchInches = pitchInches;
        this.reclineDegrees = reclineDegrees;
        this.amenities = List.copyOf(amenities);
        this.icon = new byte[2048];            // loaded ONCE per cabin product
    }

    /** Extrinsic state comes in as parameters. */
    public String render(int row, char letter, boolean occupied) {
        return row + "" + letter + " " + cabin + " " + pitchInches + "\" " + (occupied ? "X" : "_");
    }

    public String cabin() {
        return cabin;
    }

    public int pitchInches() {
        return pitchInches;
    }

    public int reclineDegrees() {
        return reclineDegrees;
    }

    public List<String> amenities() {
        return amenities;
    }

    public int iconBytes() {
        return icon.length;
    }
}
