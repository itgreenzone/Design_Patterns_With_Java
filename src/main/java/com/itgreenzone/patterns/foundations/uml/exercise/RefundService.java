package com.itgreenzone.patterns.foundations.uml.exercise;

import com.itgreenzone.patterns.foundations.uml.Booking;
import com.itgreenzone.patterns.foundations.uml.PaymentService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * HANDS-ON EXERCISE (≈20 minutes) – this time the CODE is given; you draw the UML.
 *
 *  1. Write a Mermaid CLASS diagram for RefundService, RefundResult, Booking and
 *     PaymentService (show the relationships and multiplicities).
 *  2. Write a Mermaid SEQUENCE diagram for refund(...), including the alt fragment
 *     for the three refund bands.
 *  3. Check your diagrams at https://mermaid.live, then compare with the solution README.
 */
public class RefundService {

    private final PaymentService payments;

    public RefundService(PaymentService payments) {
        this.payments = payments;
    }

    public RefundResult refund(Booking booking, LocalDate today, LocalDate departure) {
        long days = ChronoUnit.DAYS.between(today, departure);
        BigDecimal rate;
        if (days >= 7) {
            rate = BigDecimal.ONE;                       // full refund
        } else if (days >= 1) {
            rate = new BigDecimal("0.50");               // half refund
        } else {
            return new RefundResult(BigDecimal.ZERO.setScale(2), null);   // too late
        }
        BigDecimal amount = booking.total().multiply(rate).setScale(2, RoundingMode.HALF_UP);
        String ref = payments.refund(booking.paymentRef(), amount);
        return new RefundResult(amount, ref);
    }
}
