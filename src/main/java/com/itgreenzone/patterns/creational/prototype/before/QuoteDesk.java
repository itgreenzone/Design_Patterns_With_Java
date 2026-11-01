package com.itgreenzone.patterns.creational.prototype.before;

import com.itgreenzone.patterns.creational.prototype.Coverage;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * BEFORE: two bad choices.
 *  (a) rebuild the template from the rules engine for EVERY quote (slow), or
 *  (b) share ONE template object between agents (fast, but changes leak!).
 */
public class QuoteDesk {

    public static final AtomicInteger RULE_ENGINE_CALLS = new AtomicInteger();

    private final TravelQuote sharedTemplate = loadFamilyTravelGold();

    /** Pretend this hits the rules engine and database: expensive. */
    public static TravelQuote loadFamilyTravelGold() {
        RULE_ENGINE_CALLS.incrementAndGet();
        TravelQuote q = new TravelQuote("Family Travel Gold", new BigDecimal("500000.00"));
        q.addCoverage(new Coverage("Medical", new BigDecimal("500000.00")));
        q.addCoverage(new Coverage("Baggage", new BigDecimal("5000.00")));
        return q;
    }

    /** (a) correct but slow: one rules-engine call per quote. */
    public TravelQuote slowQuoteFor(String customer) {
        TravelQuote q = loadFamilyTravelGold();
        q.setCustomer(customer);
        return q;
    }

    /** (b) fast but WRONG: every agent edits the same object. */
    public TravelQuote sharedQuoteFor(String customer) {
        sharedTemplate.setCustomer(customer);
        return sharedTemplate;
    }
}
