package com.itgreenzone.patterns.structural.flyweight.after;

import java.util.ArrayList;
import java.util.List;

/** Same A380 map: 459 tiny seats sharing 4 CabinProducts. */
public class SeatMapBuilder {

    private final CabinProductFactory factory;

    public SeatMapBuilder(CabinProductFactory factory) {
        this.factory = factory;
    }

    public List<Seat> a380() {
        List<Seat> seats = new ArrayList<>();
        add(seats, 1, 3, "ACDF", factory.get("SUITES", 80, 180, List.of("Bed", "Minibar", "32in screen")));
        add(seats, 4, 16, "ACDFG", factory.get("BUSINESS", 60, 180, List.of("Flat bed", "18in screen")));
        add(seats, 17, 21, "ABCDEFGH", factory.get("PREMIUM_ECONOMY", 38, 8, List.of("Footrest", "13in screen")));
        add(seats, 22, 59, "ABCDEFGHJ", factory.get("ECONOMY", 32, 6, List.of("11in screen")));
        return seats;
    }

    private static void add(List<Seat> seats, int fromRow, int toRow, String letters, CabinProduct product) {
        for (int r = fromRow; r <= toRow; r++) {
            for (char c : letters.toCharArray()) {
                seats.add(new Seat(r, c, product));
            }
        }
    }
}
