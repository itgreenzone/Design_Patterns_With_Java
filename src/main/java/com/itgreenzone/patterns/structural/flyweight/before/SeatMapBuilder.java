package com.itgreenzone.patterns.structural.flyweight.before;

import java.util.ArrayList;
import java.util.List;

/** Builds an A380 seat map the naive way: 459 full Seat objects. */
public final class SeatMapBuilder {

    private SeatMapBuilder() {
    }

    public static List<Seat> a380() {
        List<Seat> seats = new ArrayList<>();
        add(seats, 1, 3, "ACDF", "SUITES", 80, 180, List.of("Bed", "Minibar", "32in screen"));
        add(seats, 4, 16, "ACDFG", "BUSINESS", 60, 180, List.of("Flat bed", "18in screen"));     // 13 rows × 5 = 65
        add(seats, 17, 21, "ABCDEFGH", "PREMIUM_ECONOMY", 38, 8, List.of("Footrest", "13in screen"));
        add(seats, 22, 59, "ABCDEFGHJ", "ECONOMY", 32, 6, List.of("11in screen"));
        return seats;
    }

    private static void add(List<Seat> seats, int fromRow, int toRow, String letters, String cabin,
                            int pitch, int recline, List<String> amenities) {
        for (int r = fromRow; r <= toRow; r++) {
            for (char c : letters.toCharArray()) {
                seats.add(new Seat(r, c, cabin, pitch, recline, amenities));
            }
        }
    }
}
