package com.itgreenzone.patterns.structural.facade.subsystem;

import java.util.*;

/** SUBSYSTEM: seat holds and confirmations. */
public class SeatInventory {

    private final Map<String, Set<String>> freeSeats = new HashMap<>();
    private final Map<String, String[]> holds = new HashMap<>();
    private int nextHold = 1;

    public void addFlight(String flight, String... seats) {
        freeSeats.put(flight, new TreeSet<>(Arrays.asList(seats)));
    }

    public String hold(String flight, String seat) {
        Set<String> free = freeSeats.getOrDefault(flight, Set.of());
        if (!free.contains(seat)) {
            throw new IllegalStateException("Seat " + seat + " on " + flight + " is not available");
        }
        free.remove(seat);
        String id = "H" + nextHold++;
        holds.put(id, new String[]{flight, seat});
        return id;
    }

    public void release(String holdId) {
        String[] h = holds.remove(holdId);
        if (h != null) {
            freeSeats.get(h[0]).add(h[1]);
        }
    }

    public boolean isFree(String flight, String seat) {
        return freeSeats.getOrDefault(flight, Set.of()).contains(seat);
    }
}
