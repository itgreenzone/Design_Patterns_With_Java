package com.itgreenzone.patterns.foundations.uml;

import java.math.BigDecimal;
import java.util.List;

/**
 * AGGREGATION (hollow diamond ◇): a booking groups 1..* passengers, but the
 * passengers exist independently (they can fly again on another booking).
 */
public record Booking(String reference, Flight flight, List<Passenger> passengers,
                      List<String> seatCodes, BigDecimal total, String paymentRef) {

    public Booking {
        passengers = List.copyOf(passengers);
        seatCodes = List.copyOf(seatCodes);
    }
}
