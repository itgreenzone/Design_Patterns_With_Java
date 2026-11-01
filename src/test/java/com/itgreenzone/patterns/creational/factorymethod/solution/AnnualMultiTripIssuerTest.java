package com.itgreenzone.patterns.creational.factorymethod.solution;

import com.itgreenzone.patterns.creational.factorymethod.QuoteRequest;
import com.itgreenzone.patterns.creational.factorymethod.after.IssuedPolicy;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AnnualMultiTripIssuerTest {

    @ParameterizedTest
    @CsvSource({"30, 280.00", "69, 280.00", "70, 450.00", "82, 450.00"})
    void premiumDependsOnAge(int age, BigDecimal expected) {
        IssuedPolicy p = new AnnualMultiTripIssuer().issue(new QuoteRequest("Ann", age, BigDecimal.ZERO, 0));
        assertEquals("TRAVEL-AMT", p.policy().product());
        assertEquals(expected, p.policy().annualPremium());
        assertTrue(p.policyNumber().startsWith("TRAVEL-AMT-"));
    }
}
