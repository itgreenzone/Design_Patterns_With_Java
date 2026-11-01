package com.itgreenzone.patterns.structural.facade.exercise;

import com.itgreenzone.patterns.structural.facade.BookingResult;
import com.itgreenzone.patterns.structural.facade.after.FlightBookingFacade;
import com.itgreenzone.patterns.structural.facade.subsystem.*;

/**
 * HANDS-ON EXERCISE (≈25 minutes) – add changeFlight(ticket, newFlight, newSeat).
 *
 * Steps (the facade hides all of them from the app):
 *   1. look up the booking (booking(ticket)); unknown → failed result
 *   2. hold the NEW seat (if not available → failed, nothing else changes)
 *   3. charge the NEW fare with the same card (if declined → release the new hold, failed)
 *   4. refund the OLD payment and release the OLD seat
 *   5. issue a new ticket, email the passenger, remember the new booking, forget the old one
 * Reference solution: ..facade.solution.ChangeFlightFacade
 */
public class ChangeFlightFacade extends FlightBookingFacade {

    public ChangeFlightFacade(SeatInventory seats, FareCalculator fares, PaymentGateway payments,
                              LoyaltyService loyalty, TicketIssuer tickets, NotificationService notifications) {
        super(seats, fares, payments, loyalty, tickets, notifications);
    }

    public BookingResult changeFlight(String ticket, String newFlight, String newSeat) {
        throw new UnsupportedOperationException("TODO");
    }
}
