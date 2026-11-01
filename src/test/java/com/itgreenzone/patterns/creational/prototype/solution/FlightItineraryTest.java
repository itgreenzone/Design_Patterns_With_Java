package com.itgreenzone.patterns.creational.prototype.solution;

import com.itgreenzone.patterns.creational.prototype.exercise.Segment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlightItineraryTest {

    @Test
    void changingTheCopyNeverChangesTheOriginal() {
        FlightItinerary alice = new FlightItinerary("Alice")
                .add(new Segment("SQ322", "31A")).add(new Segment("BA117", "22C"));
        FlightItinerary bob = alice.deepCopy("Bob");

        bob.segments().get(0).setSeat("31B");                 // fails with a shallow copy
        bob.add(new Segment("AA100", "10A"));                 // fails if the list is shared

        assertEquals("31A", alice.segments().get(0).seat());
        assertEquals(2, alice.segments().size());
        assertEquals("Bob", bob.traveller());
        assertEquals(3, bob.segments().size());
    }
}
