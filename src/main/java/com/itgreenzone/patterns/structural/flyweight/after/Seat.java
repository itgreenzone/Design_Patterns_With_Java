package com.itgreenzone.patterns.structural.flyweight.after;

/**
 * CONTEXT / CLIENT-SIDE OBJECT: tiny. Holds only extrinsic state (row, letter,
 * occupied) plus a REFERENCE to a shared CabinProduct.
 */
public class Seat {

    private final int row;
    private final char letter;
    private boolean occupied;
    private final CabinProduct product;        // shared flyweight

    public Seat(int row, char letter, CabinProduct product) {
        this.row = row;
        this.letter = letter;
        this.product = product;
    }

    public String render() {
        return product.render(row, letter, occupied);
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public CabinProduct product() {
        return product;
    }
}
