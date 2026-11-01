package com.itgreenzone.patterns.foundations.uml;

import java.util.concurrent.atomic.AtomicInteger;

/** A helper used only inside a method: DEPENDENCY (dashed arrow) in UML. */
final class BookingReferences {

    private static final AtomicInteger NEXT = new AtomicInteger(100);

    private BookingReferences() {
    }

    static String next() {
        return "PNR" + NEXT.incrementAndGet();
    }
}
