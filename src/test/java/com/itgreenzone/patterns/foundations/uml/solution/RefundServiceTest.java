package com.itgreenzone.patterns.foundations.uml.solution;

import com.itgreenzone.patterns.foundations.uml.*;
import com.itgreenzone.patterns.foundations.uml.exercise.RefundResult;
import com.itgreenzone.patterns.foundations.uml.exercise.RefundService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Proves the refund code behaves exactly as the solution diagrams describe. */
class RefundServiceTest {

    private final CardPaymentService payments = new CardPaymentService(new BigDecimal("10000.00"));
    private final Booking booking = new BookingService(payments).book(
            new Flight("SQ1", List.of("1A"), List.of()), List.of(new Passenger("A", "1")), List.of("1A"),
            new EconomyFare(new BigDecimal("800.00")));
    private final RefundService refunds = new RefundService(payments);
    private final LocalDate departure = LocalDate.of(2026, 12, 20);

    @Test
    void sevenOrMoreDaysGivesFullRefund() {
        RefundResult r = refunds.refund(booking, departure.minusDays(7), departure);
        assertEquals(new BigDecimal("800.00"), r.amount());
        assertNotNull(r.refundRef());
    }

    @Test
    void oneToSixDaysGivesHalf() {
        assertEquals(new BigDecimal("400.00"), refunds.refund(booking, departure.minusDays(1), departure).amount());
    }

    @Test
    void departureDayGivesNothingAndSkipsPayment() {
        RefundResult r = refunds.refund(booking, departure, departure);
        assertEquals(new BigDecimal("0.00"), r.amount());
        assertNull(r.refundRef());
        assertEquals(1, payments.ledger().size());   // only the original charge
    }
}
