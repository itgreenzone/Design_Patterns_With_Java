package com.itgreenzone.patterns.structural.facade;

/** What the mobile app sends. loyaltyId may be null. */
public record BookingRequest(String flight, String seat, String passenger, String email, String cardToken, String loyaltyId) {
}
