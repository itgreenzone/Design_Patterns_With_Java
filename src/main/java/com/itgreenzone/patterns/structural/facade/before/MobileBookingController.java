package com.itgreenzone.patterns.structural.facade.before;

import com.itgreenzone.patterns.structural.facade.BookingRequest;
import com.itgreenzone.patterns.structural.facade.BookingResult;
import com.itgreenzone.patterns.structural.facade.subsystem.*;

import java.math.BigDecimal;

/**
 * BEFORE: the mobile controller knows all SIX subsystems, the right ORDER to call
 * them, and how to undo a seat hold when payment fails. The web controller, the
 * call-centre app and the kiosk each copy this – and each gets it slightly wrong.
 */
public class MobileBookingController {

    private final SeatInventory seats;
    private final FareCalculator fares;
    private final PaymentGateway payments;
    private final LoyaltyService loyalty;
    private final TicketIssuer tickets;
    private final NotificationService notifications;

    public MobileBookingController(SeatInventory seats, FareCalculator fares, PaymentGateway payments,
                                   LoyaltyService loyalty, TicketIssuer tickets, NotificationService notifications) {
        this.seats = seats;
        this.fares = fares;
        this.payments = payments;
        this.loyalty = loyalty;
        this.tickets = tickets;
        this.notifications = notifications;
    }

    public BookingResult book(BookingRequest r) {
        String hold;
        try {
            hold = seats.hold(r.flight(), r.seat());
        } catch (IllegalStateException e) {
            return BookingResult.failed(e.getMessage());
        }
        BigDecimal total = fares.totalFare(r.flight());
        try {
            payments.charge(r.cardToken(), total);
        } catch (IllegalStateException e) {
            seats.release(hold);                       // easy to forget!
            return BookingResult.failed("Payment failed: " + e.getMessage());
        }
        if (r.loyaltyId() != null) {
            loyalty.earn(r.loyaltyId(), total);
        }
        String ticket = tickets.issue(r.flight(), r.seat(), r.passenger());
        notifications.email(r.email(), "Your ticket " + ticket + " on " + r.flight());
        return BookingResult.ok(ticket, total);
    }
}
