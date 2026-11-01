package com.itgreenzone.patterns.structural.adapter.solution;

import com.itgreenzone.patterns.structural.adapter.exercise.FxFeedClient;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FxFeedAdapterTest {

    private final FxFeedAdapter adapter = new FxFeedAdapter(new FxFeedClient(Map.of("SGDUSD", 0.74, "SGDMYR", 3.5)));

    @Test
    void invertsTheVendorQuote() {
        assertEquals(new BigDecimal("1.3514"), adapter.sgdPerUnit("USD"));
        assertEquals(new BigDecimal("0.2857"), adapter.sgdPerUnit("MYR"));
    }

    @Test
    void sgdIsOne() {
        assertEquals(new BigDecimal("1.0000"), adapter.sgdPerUnit("SGD"));
    }

    @Test
    void unknownPairIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> adapter.sgdPerUnit("XYZ"));
    }
}
