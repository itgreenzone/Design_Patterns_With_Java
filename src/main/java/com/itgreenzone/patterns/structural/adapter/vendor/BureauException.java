package com.itgreenzone.patterns.structural.adapter.vendor;

/** The vendor's CHECKED exception – forces try/catch on every caller. */
public class BureauException extends Exception {

    public BureauException(String message) {
        super(message);
    }
}
