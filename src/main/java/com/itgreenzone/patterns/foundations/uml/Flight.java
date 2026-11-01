package com.itgreenzone.patterns.foundations.uml;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * COMPOSITION (filled diamond ◆): Flight creates and owns its seats.
 * When the Flight object goes away, its Seats go with it.
 */
public class Flight {

    private final String flightNumber;
    private final Map<String, Seat> seats = new LinkedHashMap<>();   // 1 Flight ◆── 1..* Seat

    public Flight(String flightNumber, List<String> economySeats, List<String> businessSeats) {
        this.flightNumber = flightNumber;
        businessSeats.forEach(c -> seats.put(c, new Seat(c, Cabin.BUSINESS)));
        economySeats.forEach(c -> seats.put(c, new Seat(c, Cabin.ECONOMY)));
    }

    public Optional<Seat> seat(String code) {
        return Optional.ofNullable(seats.get(code));
    }

    public long availableSeats() {
        return seats.values().stream().filter(s -> !s.isBooked()).count();
    }

    public Map<String, Seat> seats() {
        return Collections.unmodifiableMap(seats);
    }

    public String flightNumber() {
        return flightNumber;
    }
}
