package com.itgreenzone.patterns.behavioral.iterator.solution;

import com.itgreenzone.patterns.behavioral.iterator.exercise.Leg;

import java.util.Iterator;
import java.util.List;

/** Reference solution: an Iterable aggregate, so for-each skips cancelled legs automatically. */
public record FlightItinerary(List<Leg> legs) implements Iterable<Leg> {

    public FlightItinerary {
        legs = List.copyOf(legs);
    }

    @Override
    public Iterator<Leg> iterator() {
        return new ActiveLegIterator(legs);
    }
}
