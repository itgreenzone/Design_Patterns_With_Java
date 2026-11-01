package com.itgreenzone.patterns.foundations.uml;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingServiceTest {

    private Flight flight;
    private CardPaymentService payments;
    private BookingService service;

    @BeforeEach
    void setUp() {
        flight = new Flight("SQ322", List.of("31A", "31B", "31C"), List.of("11A"));
        payments = new CardPaymentService(new BigDecimal("3000.00"));
        service = new BookingService(payments);
    }

    @Test
    void booksSeatsChargesOnceAndAddsMiles() {
        Passenger alice = new Passenger("Alice", "E1", new LoyaltyAccount("KF-1", 100));
        Passenger bob = new Passenger("Bob", "E2");
        Booking b = service.book(flight, List.of(alice, bob), List.of("31A", "31B"), new EconomyFare(new BigDecimal("1000.00")));

        assertEquals(new BigDecimal("2000.00"), b.total());
        assertEquals(2, flight.availableSeats());
        assertEquals(1100, alice.loyaltyAccount().orElseThrow().miles());   // opt fragment
        assertTrue(bob.loyaltyAccount().isEmpty());
        assertEquals(1, payments.ledger().size());
    }

    @Test
    void takenSeatStopsTheBooking() {   // alt fragment
        service.book(flight, List.of(new Passenger("A", "1")), List.of("31A"), new EconomyFare(new BigDecimal("100.00")));
        assertThrows(IllegalStateException.class,
                () -> service.book(flight, List.of(new Passenger("B", "2")), List.of("31A"), new EconomyFare(new BigDecimal("100.00"))));
    }

    @Test
    void declinedPaymentLeavesSeatsFree() {
        assertThrows(IllegalStateException.class, () -> service.book(flight,
                List.of(new Passenger("A", "1")), List.of("11A"), new BusinessFare(new BigDecimal("5000.00"))));
        assertEquals(4, flight.availableSeats());
    }

    @Test
    void businessFareAddsPremiumAndDoubleMiles() {
        Fare fare = new BusinessFare(new BigDecimal("1000.00"));
        assertEquals(new BigDecimal("1150.00"), fare.price());
        assertEquals(2000, fare.milesEarned());
    }

    @Test
    void flightOwnsItsSeats() {   // composition
        assertEquals(Cabin.BUSINESS, flight.seat("11A").orElseThrow().cabin());
        assertTrue(flight.seat("99Z").isEmpty());
        assertThrows(UnsupportedOperationException.class, () -> flight.seats().clear());
    }
}
