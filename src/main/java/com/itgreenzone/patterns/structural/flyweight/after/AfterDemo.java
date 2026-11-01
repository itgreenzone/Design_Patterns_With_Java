package com.itgreenzone.patterns.structural.flyweight.after;

import java.util.List;

/** 300 A380 flights a day: compare the estimated memory. */
public class AfterDemo {

    public static void main(String[] args) {
        CabinProductFactory factory = new CabinProductFactory();
        List<Seat> seats = new SeatMapBuilder(factory).a380();
        seats.get(0).setOccupied(true);
        System.out.println(seats.get(0).render() + " | " + seats.get(seats.size() - 1).render());

        long totalSeats = seats.size() * 300L;
        System.out.printf("Seats: %,d  shared products: %d%n", totalSeats, factory.distinctProducts());
        System.out.printf("Without flyweight ≈ %,d MB%n", MemoryEstimate.withoutFlyweight(totalSeats) / 1_000_000);
        System.out.printf("With flyweight    ≈ %,d MB%n", MemoryEstimate.withFlyweight(totalSeats, factory.distinctProducts()) / 1_000_000);
    }
}
