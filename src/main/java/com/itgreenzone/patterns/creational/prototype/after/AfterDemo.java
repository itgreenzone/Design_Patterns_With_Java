package com.itgreenzone.patterns.creational.prototype.after;

import java.math.BigDecimal;

/** Two agents, two independent copies of the same template. */
public class AfterDemo {

    public static void main(String[] args) {
        QuoteTemplateRegistry registry = new QuoteTemplateRegistry();
        TravelQuote alice = registry.newQuote("FAMILY_TRAVEL_GOLD", "Alice Tan");
        TravelQuote bob = registry.newQuote("FAMILY_TRAVEL_GOLD", "Bob Lim");
        bob.coverage("Baggage").setLimit(new BigDecimal("8000.00"));   // Bob upgrades baggage
        System.out.println("Alice baggage: " + alice.coverage("Baggage").limit());
        System.out.println("Bob baggage:   " + bob.coverage("Baggage").limit());
        System.out.println("Rules engine calls: " + QuoteTemplateRegistry.RULE_ENGINE_CALLS.get());
    }
}
