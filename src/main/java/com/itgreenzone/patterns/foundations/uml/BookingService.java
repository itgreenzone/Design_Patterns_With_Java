package com.itgreenzone.patterns.foundations.uml;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Its flow matches the lesson's sequence diagram, including the
 * loop (each seat), alt (seat taken) and opt (loyalty account) fragments.
 */
public class BookingService {

    private final PaymentService payments;

    public BookingService(PaymentService payments) {
        this.payments = Objects.requireNonNull(payments);
    }

    public Booking book(Flight flight, List<Passenger> passengers, List<String> seatCodes, Fare fare) {
        if (passengers.isEmpty() || passengers.size() != seatCodes.size()) {
            throw new IllegalArgumentException("One seat per passenger is required");
        }
        // loop [each seat]: check availability
        List<Seat> seats = seatCodes.stream()
                .map(code -> flight.seat(code).orElseThrow(() -> new IllegalArgumentException("No seat " + code)))
                .toList();
        for (Seat seat : seats) {
            // alt [seat already booked]
            if (seat.isBooked()) {
                throw new IllegalStateException("Seat " + seat.code() + " is taken");
            }
        }
        BigDecimal total = fare.price().multiply(BigDecimal.valueOf(passengers.size()));
        String paymentRef = payments.charge(total);             // pay BEFORE seats are held
        seats.forEach(Seat::book);
        // opt [passenger has loyalty account]
        passengers.forEach(p -> p.loyaltyAccount().ifPresent(a -> a.addMiles(fare.milesEarned())));
        return new Booking(BookingReferences.next(), flight, passengers, seatCodes, total, paymentRef);
    }
}
