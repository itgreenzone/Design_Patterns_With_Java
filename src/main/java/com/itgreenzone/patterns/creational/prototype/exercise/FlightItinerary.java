package com.itgreenzone.patterns.creational.prototype.exercise;

import java.util.ArrayList;
import java.util.List;

/**
 * HANDS-ON EXERCISE (≈20 minutes)
 *
 * A travel agent copies a "Singapore → London → New York" itinerary for a second
 * traveller, then changes that traveller's seats. Implement deepCopy() so that
 * changing a seat on the copy NEVER changes the original. Then write a test that
 * would FAIL with a shallow copy and PASS with yours.
 * Hint: Segment already has a copy constructor.
 * Reference solution: ..prototype.solution.FlightItinerary
 */
public class FlightItinerary {

    private final String traveller;
    private final List<Segment> segments = new ArrayList<>();

    public FlightItinerary(String traveller) {
        this.traveller = traveller;
    }

    public FlightItinerary add(Segment s) {
        segments.add(s);
        return this;
    }

    public List<Segment> segments() {
        return segments;
    }

    public String traveller() {
        return traveller;
    }

    public FlightItinerary deepCopy(String newTraveller) {
        throw new UnsupportedOperationException("TODO");
    }
}
