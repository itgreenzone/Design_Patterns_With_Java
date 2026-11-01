package com.itgreenzone.patterns.behavioral.iterator.exercise;

import java.util.Iterator;
import java.util.List;

/**
 * HANDS-ON EXERCISE (≈20 minutes)
 *
 * Iterate over an itinerary's legs, SKIPPING cancelled ones.
 *   - hasNext() must be safe to call many times in a row
 *   - next() throws NoSuchElementException when there are no more active legs
 *   - don't copy the list; walk it with an index
 * Then make FlightItinerary (solution package) Iterable<Leg> so for-each works.
 * Reference solution: ..iterator.solution
 */
public class ActiveLegIterator implements Iterator<Leg> {

    public ActiveLegIterator(List<Leg> legs) {
        // TODO
    }

    @Override
    public boolean hasNext() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public Leg next() {
        throw new UnsupportedOperationException("TODO");
    }
}
