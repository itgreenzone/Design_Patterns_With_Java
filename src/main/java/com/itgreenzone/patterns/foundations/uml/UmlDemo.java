package com.itgreenzone.patterns.foundations.uml;

import java.math.BigDecimal;
import java.util.List;

/** Run me: books two passengers on SQ322 exactly as the sequence diagram shows. */
public class UmlDemo {

    public static void main(String[] args) {
        Flight sq322 = new Flight("SQ322", List.of("31A", "31B", "31C"), List.of("11A", "11D"));
        Passenger alice = new Passenger("Alice Tan", "E1234567", new LoyaltyAccount("KF-001", 10_000));
        Passenger bob = new Passenger("Bob Lim", "E7654321");
        BookingService service = new BookingService(new CardPaymentService(new BigDecimal("10000.00")));

        Booking booking = service.book(sq322, List.of(alice, bob), List.of("31A", "31B"), new EconomyFare(new BigDecimal("1250.00")));
        System.out.println(booking.reference() + " total SGD " + booking.total() + ", seats left " + sq322.availableSeats());
        System.out.println("Alice's miles: " + alice.loyaltyAccount().orElseThrow().miles());
    }
}
