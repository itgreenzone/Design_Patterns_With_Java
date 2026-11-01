package com.itgreenzone.patterns.creational.builder.solution;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class InsuranceQuoteRequestTest {

    @Test
    void defaultsApply() {
        InsuranceQuoteRequest q = InsuranceQuoteRequest.builder("Alice", 35).build();
        assertEquals(new BigDecimal("100000.00"), q.sumInsured());
        assertFalse(q.smoker());
        assertTrue(q.riders().isEmpty());
    }

    @Test
    void fluentOptionalsAndImmutableRiders() {
        InsuranceQuoteRequest q = InsuranceQuoteRequest.builder("Alice", 35)
                .sumInsured(new BigDecimal("500000.00")).smoker().rider("CRITICAL_ILLNESS").build();
        assertTrue(q.smoker());
        assertEquals(1, q.riders().size());
        assertThrows(UnsupportedOperationException.class, () -> q.riders().add("HACK"));
    }

    @Test
    void validationRules() {
        assertThrows(IllegalStateException.class, () -> InsuranceQuoteRequest.builder("A", 17).build());
        assertThrows(IllegalStateException.class, () -> InsuranceQuoteRequest.builder("A", 40).sumInsured(new BigDecimal("5000.00")).build());
        assertThrows(IllegalStateException.class, () -> InsuranceQuoteRequest.builder("A", 40).sumInsured(new BigDecimal("2000000.01")).build());
    }
}
