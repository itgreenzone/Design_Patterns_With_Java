package com.itgreenzone.patterns.creational.prototype.after;

import com.itgreenzone.patterns.creational.prototype.Coverage;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * REGISTRY (prototype manager): loads each expensive template ONCE,
 * then hands out fresh deep copies.
 */
public class QuoteTemplateRegistry {

    public static final AtomicInteger RULE_ENGINE_CALLS = new AtomicInteger();

    private final Map<String, TravelQuote> templates = new ConcurrentHashMap<>();

    public QuoteTemplateRegistry() {
        templates.put("FAMILY_TRAVEL_GOLD", loadFromRulesEngine("Family Travel Gold", "500000.00", "5000.00"));
        templates.put("SOLO_TRAVEL_SILVER", loadFromRulesEngine("Solo Travel Silver", "150000.00", "2000.00"));
    }

    private static TravelQuote loadFromRulesEngine(String plan, String medical, String baggage) {
        RULE_ENGINE_CALLS.incrementAndGet();          // the expensive part, done once per template
        TravelQuote q = new TravelQuote(plan, new BigDecimal(medical));
        q.addCoverage(new Coverage("Medical", new BigDecimal(medical)));
        q.addCoverage(new Coverage("Baggage", new BigDecimal(baggage)));
        return q;
    }

    public void register(String key, TravelQuote template) {
        templates.put(key, template);
    }

    /** CLIENT entry point: never returns the template itself, always a copy. */
    public TravelQuote newQuote(String key, String customer) {
        TravelQuote template = templates.get(key);
        if (template == null) {
            throw new IllegalArgumentException("No template " + key);
        }
        TravelQuote quote = template.copy();
        quote.setCustomer(customer);
        return quote;
    }
}
