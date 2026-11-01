package com.itgreenzone.patterns.creational.factorymethod;

import java.math.BigDecimal;

/** What the customer filled in on the quote screen. Not every product uses every field. */
public record QuoteRequest(String customer, int age, BigDecimal sumInsured, int tripDays) {
}
