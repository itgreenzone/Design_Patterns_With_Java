package com.itgreenzone.patterns.creational.prototype;

import com.itgreenzone.patterns.creational.prototype.after.QuoteTemplateRegistry;
import com.itgreenzone.patterns.creational.prototype.after.TravelQuote;
import com.itgreenzone.patterns.creational.prototype.before.QuoteDesk;
import com.itgreenzone.patterns.creational.prototype.modern.CoverageItem;
import com.itgreenzone.patterns.creational.prototype.modern.Quote;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PrototypeTest {

    private static final BigDecimal UPGRADED = new BigDecimal("8000.00");

    @Test
    void sharedTemplateLeaksBetweenCustomers() {
        QuoteDesk desk = new QuoteDesk();
        var alice = desk.sharedQuoteFor("Alice");
        desk.sharedQuoteFor("Bob");
        assertEquals("Bob", alice.customer(), "Alice's quote now says Bob!");
    }

    @Test
    void slowPathCallsRulesEngineEveryTime() {
        QuoteDesk desk = new QuoteDesk();
        int before = QuoteDesk.RULE_ENGINE_CALLS.get();
        desk.slowQuoteFor("A");
        desk.slowQuoteFor("B");
        assertEquals(before + 2, QuoteDesk.RULE_ENGINE_CALLS.get());
    }

    @Test
    void registryLoadsOnceAndHandsOutIndependentCopies() {
        int before = QuoteTemplateRegistry.RULE_ENGINE_CALLS.get();
        QuoteTemplateRegistry registry = new QuoteTemplateRegistry();
        TravelQuote alice = registry.newQuote("FAMILY_TRAVEL_GOLD", "Alice");
        TravelQuote bob = registry.newQuote("FAMILY_TRAVEL_GOLD", "Bob");
        TravelQuote carol = registry.newQuote("FAMILY_TRAVEL_GOLD", "Carol");
        assertEquals(before + 2, QuoteTemplateRegistry.RULE_ENGINE_CALLS.get(), "2 templates loaded once each");

        bob.coverage("Baggage").setLimit(UPGRADED);
        assertEquals(new BigDecimal("5000.00"), alice.coverage("Baggage").limit());
        assertEquals(new BigDecimal("5000.00"), carol.coverage("Baggage").limit());
        assertEquals("Alice", alice.customer());
        assertNotSame(alice.coverages(), bob.coverages());
    }

    @Test
    void shallowCopySharesNestedObjects() {
        TravelQuote original = new QuoteTemplateRegistry().newQuote("SOLO_TRAVEL_SILVER", "Dan");
        TravelQuote shallow = original.shallowCopy();
        shallow.coverage("Medical").setLimit(BigDecimal.ONE);
        assertEquals(BigDecimal.ONE, original.coverage("Medical").limit(), "shallow copy changed the original");
    }

    @Test
    void deepCopyKeepsOriginalSafe() {
        TravelQuote original = new QuoteTemplateRegistry().newQuote("SOLO_TRAVEL_SILVER", "Dan");
        TravelQuote deep = original.copy();
        deep.coverage("Medical").setLimit(BigDecimal.ONE);
        assertEquals(new BigDecimal("150000.00"), original.coverage("Medical").limit());
    }

    @Test
    void unknownTemplateIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new QuoteTemplateRegistry().newQuote("NOPE", "X"));
    }

    @Test
    void immutableRecordsMakeCopyingTrivial() {
        Quote template = new Quote("Family Travel Gold", null, new BigDecimal("500000.00"),
                List.of(new CoverageItem("Medical", new BigDecimal("500000.00")), new CoverageItem("Baggage", new BigDecimal("5000.00"))));
        Quote bob = template.withCustomer("Bob").withCoverageLimit("Baggage", UPGRADED);
        assertNull(template.customer());
        assertEquals(new BigDecimal("5000.00"), template.coverages().get(1).limit());
        assertEquals(UPGRADED, bob.coverages().get(1).limit());
        assertSame(template.coverages().get(0), bob.coverages().get(0), "unchanged immutable parts are safely shared");
        assertThrows(UnsupportedOperationException.class, () -> bob.coverages().clear());
    }
}
