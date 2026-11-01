package com.itgreenzone.patterns.foundations.solid;

import java.math.BigDecimal;

/** An insurance claim, shared by every SOLID example in this lesson. */
public record Claim(String id, ClaimType type, BigDecimal amount, String customerEmail) {
}
