package com.itgreenzone.patterns.structural.adapter;

import com.itgreenzone.patterns.structural.adapter.after.*;
import com.itgreenzone.patterns.structural.adapter.modern.ScoreAdapters;
import com.itgreenzone.patterns.structural.adapter.vendor.LegacyBureauClient;
import com.itgreenzone.patterns.structural.adapter.vendor.ModernScoreApi;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;
import java.util.OptionalInt;

import static org.junit.jupiter.api.Assertions.*;

class AdapterTest {

    private final LegacyBureauClient legacy = new LegacyBureauClient(Map.of("A", 1000, "B", 600, "C", 400, "D", 0));

    @ParameterizedTest
    @CsvSource({"A, 850", "B, 630", "C, 520", "D, 300"})
    void objectAdapterConvertsScale(String id, int expected) {
        assertEquals(expected, new BureauCreditScoreAdapter(legacy).scoreFor(id).value());
    }

    @Test
    void checkedVendorExceptionIsTranslated() {
        CreditCheckUnavailableException e = assertThrows(CreditCheckUnavailableException.class,
                () -> new BureauCreditScoreAdapter(legacy).scoreFor("NOPE"));
        assertNotNull(e.getCause());
    }

    @ParameterizedTest
    @CsvSource({"A, APPROVE", "B, REFER", "C, DECLINE", "NOPE, REFER"})
    void loanRulesAreTheSameBeforeAndAfter(String id, String expected) {
        assertEquals(expected, new LoanService(new BureauCreditScoreAdapter(legacy)).decide(id));
        assertEquals(expected, new com.itgreenzone.patterns.structural.adapter.before.LoanService(legacy).decide(id));
    }

    @Test
    void secondVendorPlugsInWithoutChangingLoanService() {
        LoanService service = new LoanService(new ModernBureauAdapter(new ModernScoreApi(Map.of("X", 900, "Y", 300))));
        assertEquals("APPROVE", service.decide("X"));
        assertEquals("DECLINE", service.decide("Y"));
        assertEquals("REFER", service.decide("missing"));
    }

    @Test
    void classAdapterWorksButExposesVendorMethods() throws Exception {
        BureauClassAdapter adapter = new BureauClassAdapter(Map.of("A", 1000));
        assertEquals(850, adapter.scoreFor("A").value());
        assertTrue(adapter.fetchReportXml("A").contains("<rating>"), "vendor API leaks through inheritance");
    }

    @Test
    void lambdaAdapterHandlesAnyScale() {
        CreditScoreProvider p = ScoreAdapters.fromScale(id -> id.equals("Z") ? OptionalInt.of(50) : OptionalInt.empty(), 0, 100);
        assertEquals(575, p.scoreFor("Z").value());
        assertEquals("REFER", new LoanService(p).decide("unknown"));
    }

    @Test
    void domainScoreRejectsOutOfRangeValues() {
        assertThrows(IllegalArgumentException.class, () -> new CreditScore(900));
        assertEquals("EXCELLENT", new CreditScore(800).band());
    }
}
