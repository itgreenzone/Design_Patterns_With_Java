package com.itgreenzone.patterns.structural.facade.after;

import com.itgreenzone.patterns.structural.facade.BookingRequest;
import com.itgreenzone.patterns.structural.facade.subsystem.*;

import java.math.BigDecimal;

/** Mobile, web and kiosk all call ONE method. */
public class AfterDemo {

    public static void main(String[] args) {
        SeatInventory seats = new SeatInventory();
        seats.addFlight("SQ322", "31A", "31B");
        FareCalculator fares = new FareCalculator();
        fares.setBaseFare("SQ322", new BigDecimal("1200.00"));
        FlightBookingFacade facade = new FlightBookingFacade(seats, fares, new PaymentGateway(),
                new LoyaltyService(), new TicketIssuer(), new NotificationService());

        System.out.println(facade.bookFlight(new BookingRequest("SQ322", "31A", "Alice Tan", "alice@example.com", "tok_ok", "KF-1")));
        System.out.println(facade.bookFlight(new BookingRequest("SQ322", "31B", "Bob Lim", "bob@example.com", "DECLINE_tok", null)));
        System.out.println("31B free again? " + seats.isFree("SQ322", "31B"));
    }
}
