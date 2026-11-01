package com.itgreenzone.patterns.structural.facade;

import com.itgreenzone.patterns.structural.facade.after.FlightBookingFacade;
import com.itgreenzone.patterns.structural.facade.before.MobileBookingController;
import com.itgreenzone.patterns.structural.facade.solution.ChangeFlightFacade;
import com.itgreenzone.patterns.structural.facade.subsystem.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class FacadeTest {

    private SeatInventory seats;
    private FareCalculator fares;
    private PaymentGateway payments;
    private LoyaltyService loyalty;
    private NotificationService notifications;
    private ChangeFlightFacade facade;

    @BeforeEach
    void setUp() {
        seats = new SeatInventory();
        seats.addFlight("SQ322", "31A", "31B");
        seats.addFlight("SQ308", "40C");
        fares = new FareCalculator();
        fares.setBaseFare("SQ322", new BigDecimal("1000.00"));
        fares.setBaseFare("SQ308", new BigDecimal("800.00"));
        payments = new PaymentGateway();
        loyalty = new LoyaltyService();
        notifications = new NotificationService();
        facade = new ChangeFlightFacade(seats, fares, payments, loyalty, new TicketIssuer(), notifications);
    }

    private BookingRequest req(String seat, String card) {
        return new BookingRequest("SQ322", seat, "Alice", "alice@example.com", card, "KF-1");
    }

    @Test
    void oneCallRunsTheWholeFlow() {
        BookingResult r = facade.bookFlight(req("31A", "tok"));
        assertTrue(r.success());
        assertEquals(new BigDecimal("1150.00"), r.total());
        assertFalse(seats.isFree("SQ322", "31A"));
        assertEquals(1150, loyalty.balance("KF-1"));
        assertEquals(1, notifications.sent().size());
    }

    @Test
    void declinedPaymentReleasesTheSeat() {
        BookingResult r = facade.bookFlight(req("31B", "DECLINE_me"));
        assertFalse(r.success());
        assertTrue(seats.isFree("SQ322", "31B"));
        assertEquals(0, loyalty.balance("KF-1"));
        assertTrue(notifications.sent().isEmpty());
    }

    @Test
    void unavailableSeatFailsCleanly() {
        facade.bookFlight(req("31A", "tok"));
        assertFalse(facade.bookFlight(req("31A", "tok")).success());
    }

    @Test
    void cancelRefundsFreesSeatAndReversesMiles() {
        String ticket = facade.bookFlight(req("31A", "tok")).ticket();
        assertTrue(facade.cancelBooking(ticket).success());
        assertTrue(seats.isFree("SQ322", "31A"));
        assertEquals(0, loyalty.balance("KF-1"));
        assertTrue(payments.ledger().get(1).startsWith("REFUND"));
        assertFalse(facade.cancelBooking(ticket).success(), "can't cancel twice");
    }

    @Test
    void sameResultAsTheBeforeController() {
        SeatInventory s2 = new SeatInventory();
        s2.addFlight("SQ322", "31A", "31B");
        MobileBookingController before = new MobileBookingController(s2, fares, new PaymentGateway(),
                new LoyaltyService(), new TicketIssuer(), new NotificationService());
        assertEquals(before.book(req("31A", "tok")), facade.bookFlight(req("31A", "tok")));
    }

    @Test
    void subsystemsStayUsableDirectly() {
        assertEquals(new BigDecimal("920.00"), fares.totalFare("SQ308"), "a fare-quote screen needs only one subsystem");
    }

    @Test
    void changeFlightSolution() {
        String ticket = facade.bookFlight(req("31A", "tok")).ticket();
        BookingResult changed = facade.changeFlight(ticket, "SQ308", "40C");
        assertTrue(changed.success());
        assertEquals(new BigDecimal("920.00"), changed.total());
        assertTrue(seats.isFree("SQ322", "31A"));
        assertFalse(seats.isFree("SQ308", "40C"));
        assertTrue(payments.ledger().stream().anyMatch(l -> l.startsWith("REFUND PAY-1")));
        assertFalse(facade.cancelBooking(ticket).success(), "old ticket is gone");
        assertTrue(facade.cancelBooking(changed.ticket()).success());
    }

    @Test
    void changeFlightToTakenSeatChangesNothing() {
        String ticket = facade.bookFlight(req("31A", "tok")).ticket();
        facade.bookFlight(new BookingRequest("SQ308", "40C", "Bob", "b@x.com", "tok", null));
        assertFalse(facade.changeFlight(ticket, "SQ308", "40C").success());
        assertFalse(seats.isFree("SQ322", "31A"), "original booking kept");
    }

    @Test
    void plainFacadeIsUsableWithoutTheExtension() {
        FlightBookingFacade plain = new FlightBookingFacade(seats, fares, payments, loyalty, new TicketIssuer(), notifications);
        assertTrue(plain.bookFlight(req("31B", "tok")).success());
    }
}
