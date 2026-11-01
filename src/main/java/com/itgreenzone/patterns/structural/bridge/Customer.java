package com.itgreenzone.patterns.structural.bridge;

/** Where a customer can be reached. */
public record Customer(String name, String mobile, String email, String deviceToken) {
}
