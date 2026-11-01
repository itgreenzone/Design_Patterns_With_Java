package com.itgreenzone.patterns.structural.facade;

import java.math.BigDecimal;

public record BookingResult(boolean success, String ticket, BigDecimal total, String message) {

    public static BookingResult ok(String ticket, BigDecimal total) {
        return new BookingResult(true, ticket, total, "Booked");
    }

    public static BookingResult failed(String message) {
        return new BookingResult(false, null, BigDecimal.ZERO.setScale(2), message);
    }
}
