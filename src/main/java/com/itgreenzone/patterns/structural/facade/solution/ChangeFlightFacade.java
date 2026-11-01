package com.itgreenzone.patterns.structural.facade.solution;

import com.itgreenzone.patterns.structural.facade.BookingRequest;
import com.itgreenzone.patterns.structural.facade.BookingResult;
import com.itgreenzone.patterns.structural.facade.after.FlightBookingFacade;
import com.itgreenzone.patterns.structural.facade.subsystem.*;

import java.math.BigDecimal;

/** Reference solution: one more high-level operation; subsystems untouched. */
public class ChangeFlightFacade extends FlightBookingFacade {

    public ChangeFlightFacade(SeatInventory seats, FareCalculator fares, PaymentGateway payments,
                              LoyaltyService loyalty, TicketIssuer tickets, NotificationService notifications) {
        super(seats, fares, payments, loyalty, tickets, notifications);
    }

    public BookingResult changeFlight(String ticket, String newFlight, String newSeat) {
        var old = booking(ticket).orElse(null);
        if (old == null) {
            return BookingResult.failed("Unknown ticket " + ticket);
        }
        String newHold;
        try {
            newHold = seats.hold(newFlight, newSeat);
        } catch (IllegalStateException e) {
            return BookingResult.failed(e.getMessage());
        }
        BigDecimal newTotal = fares.totalFare(newFlight);
        String newPayment;
        try {
            newPayment = payments.charge(old.request().cardToken(), newTotal);
        } catch (IllegalStateException e) {
            seats.release(newHold);
            return BookingResult.failed("Payment failed: " + e.getMessage());
        }
        payments.refund(old.paymentRef(), old.total());
        seats.release(old.holdId());
        BookingRequest r = old.request();
        BookingRequest changed = new BookingRequest(newFlight, newSeat, r.passenger(), r.email(), r.cardToken(), r.loyaltyId());
        String newTicket = tickets.issue(newFlight, newSeat, r.passenger());
        notifications.email(r.email(), "Changed to " + newFlight + ", new ticket " + newTicket);
        forget(ticket);
        remember(newTicket, new BookingRecord(changed, newHold, newPayment, newTotal));
        return BookingResult.ok(newTicket, newTotal);
    }
}
