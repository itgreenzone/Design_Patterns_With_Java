package com.itgreenzone.patterns.structural.facade.after;

import com.itgreenzone.patterns.structural.facade.BookingRequest;
import com.itgreenzone.patterns.structural.facade.BookingResult;
import com.itgreenzone.patterns.structural.facade.subsystem.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * FACADE: one simple entry point for the common booking flows.
 * The ORDER of calls and the FAILURE handling (release the seat if payment fails)
 * live here, once. Subsystems stay public, so advanced clients can still use them.
 */
public class FlightBookingFacade {

    /** What the facade remembers so it can cancel later. */
    public record BookingRecord(BookingRequest request, String holdId, String paymentRef, BigDecimal total) {
    }

    protected final SeatInventory seats;
    protected final FareCalculator fares;
    protected final PaymentGateway payments;
    protected final LoyaltyService loyalty;
    protected final TicketIssuer tickets;
    protected final NotificationService notifications;
    private final Map<String, BookingRecord> bookings = new HashMap<>();

    public FlightBookingFacade(SeatInventory seats, FareCalculator fares, PaymentGateway payments,
                               LoyaltyService loyalty, TicketIssuer tickets, NotificationService notifications) {
        this.seats = seats;
        this.fares = fares;
        this.payments = payments;
        this.loyalty = loyalty;
        this.tickets = tickets;
        this.notifications = notifications;
    }

    public BookingResult bookFlight(BookingRequest r) {
        String hold;
        try {
            hold = seats.hold(r.flight(), r.seat());                     // 1. hold the seat
        } catch (IllegalStateException e) {
            return BookingResult.failed(e.getMessage());
        }
        BigDecimal total = fares.totalFare(r.flight());                  // 2. price it
        String paymentRef;
        try {
            paymentRef = payments.charge(r.cardToken(), total);          // 3. take payment
        } catch (IllegalStateException e) {
            seats.release(hold);                                          //    ...or undo step 1
            return BookingResult.failed("Payment failed: " + e.getMessage());
        }
        if (r.loyaltyId() != null) {
            loyalty.earn(r.loyaltyId(), total);                           // 4. miles
        }
        String ticket = tickets.issue(r.flight(), r.seat(), r.passenger());   // 5. ticket
        notifications.email(r.email(), "Your ticket " + ticket + " on " + r.flight());   // 6. tell them
        bookings.put(ticket, new BookingRecord(r, hold, paymentRef, total));
        return BookingResult.ok(ticket, total);
    }

    public BookingResult cancelBooking(String ticket) {
        BookingRecord b = bookings.remove(ticket);
        if (b == null) {
            return BookingResult.failed("Unknown ticket " + ticket);
        }
        payments.refund(b.paymentRef(), b.total());
        seats.release(b.holdId());
        if (b.request().loyaltyId() != null) {
            loyalty.reverse(b.request().loyaltyId(), b.total());
        }
        notifications.email(b.request().email(), "Ticket " + ticket + " cancelled and refunded");
        return new BookingResult(true, ticket, b.total(), "Cancelled");
    }

    protected Optional<BookingRecord> booking(String ticket) {
        return Optional.ofNullable(bookings.get(ticket));
    }

    protected void remember(String ticket, BookingRecord record) {
        bookings.put(ticket, record);
    }

    protected void forget(String ticket) {
        bookings.remove(ticket);
    }
}
