package com.itgreenzone.patterns.structural.bridge;

/** What was actually sent, returned so tests (and audit logs) can check it. */
public record Delivery(String channel, String to, String text) {
}
