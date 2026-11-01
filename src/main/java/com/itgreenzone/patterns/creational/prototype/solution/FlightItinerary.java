package com.itgreenzone.patterns.creational.prototype.solution;

import com.itgreenzone.patterns.creational.prototype.exercise.Segment;

import java.util.ArrayList;
import java.util.List;

/** Reference solution: a new list AND a new Segment for every leg. */
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
        FlightItinerary copy = new FlightItinerary(newTraveller);
        segments.forEach(s -> copy.add(new Segment(s)));
        return copy;
    }
}
