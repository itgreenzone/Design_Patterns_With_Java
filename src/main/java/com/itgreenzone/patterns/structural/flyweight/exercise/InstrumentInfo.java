package com.itgreenzone.patterns.structural.flyweight.exercise;

/** Heavy, shared reference data about a traded instrument (the intrinsic state). */
public record InstrumentInfo(String symbol, String name, String exchange, String currency, int lotSize, String description) {
}
