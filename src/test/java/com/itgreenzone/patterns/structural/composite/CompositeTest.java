package com.itgreenzone.patterns.structural.composite;

import com.itgreenzone.patterns.structural.composite.after.*;
import com.itgreenzone.patterns.structural.composite.before.PortfolioReport;
import com.itgreenzone.patterns.structural.composite.modern.Assets;
import com.itgreenzone.patterns.structural.composite.modern.Assets.Fund;
import com.itgreenzone.patterns.structural.composite.modern.Assets.Holding;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompositeTest {

    private Portfolio retirement;
    private Portfolio equities;
    private Bond bond;

    @BeforeEach
    void setUp() {
        equities = new Portfolio("Global Equities")
                .add(new Stock("D05.SI", 1000, new BigDecimal("35.50")))                       // 35,500.00
                .add(new UnitTrust("World", new BigDecimal("2000"), new BigDecimal("12.40"), new BigDecimal("0.015")));  // 24,800.00
        bond = new Bond("SGS 2034", new BigDecimal("50000.00"), new BigDecimal("98.50"));      // 49,250.00
        retirement = new Portfolio("Retirement").add(equities).add(bond);
    }

    @Test
    void anyLevelAnswersTheSameQuestion() {
        assertEquals(new BigDecimal("60300.00"), equities.marketValue());
        assertEquals(new BigDecimal("109550.00"), retirement.marketValue());
        assertEquals(new BigDecimal("49250.00"), bond.marketValue());
    }

    @Test
    void feesRollUpThroughTheTree() {
        // 35.50 (stock) + 372.00 (unit trust) + 24.63 (bond)
        assertEquals(new BigDecimal("432.13"), retirement.annualFees());
    }

    @Test
    void clientTreatsLeavesAndCompositesUniformly() {
        List<PortfolioComponent> mixed = List.of(retirement, equities, bond);
        BigDecimal sum = mixed.stream().map(PortfolioComponent::marketValue).reduce(BigDecimal.ZERO, BigDecimal::add);
        assertEquals(new BigDecimal("219100.00"), sum);
    }

    @Test
    void beforeAndAfterAgree() {
        var b = new com.itgreenzone.patterns.structural.composite.before.Portfolio("Retirement")
                .add(new com.itgreenzone.patterns.structural.composite.before.Portfolio("Global Equities")
                        .add(new com.itgreenzone.patterns.structural.composite.before.Stock("D05.SI", 1000, new BigDecimal("35.50")))
                        .add(new com.itgreenzone.patterns.structural.composite.before.UnitTrust("World", new BigDecimal("2000"), new BigDecimal("12.40"), new BigDecimal("0.015"))))
                .add(new com.itgreenzone.patterns.structural.composite.before.Bond("SGS 2034", new BigDecimal("50000.00"), new BigDecimal("98.50")));
        PortfolioReport report = new PortfolioReport();
        assertEquals(retirement.marketValue(), report.marketValue(b));
        assertEquals(retirement.annualFees(), report.annualFees(b));
    }

    @Test
    void safetyDesignAndSelfContainmentGuard() {
        assertThrows(IllegalArgumentException.class, () -> retirement.add(retirement));
        assertTrue(retirement.remove(bond));
        assertEquals(new BigDecimal("60300.00"), retirement.marketValue());
        assertThrows(UnsupportedOperationException.class, () -> retirement.children().clear());
    }

    @Test
    void sealedTreeWithPatternMatching() {
        Fund fund = new Fund("Retirement", List.of(
                new Fund("Equities", List.of(new Holding("A", new BigDecimal("1000.00"), new BigDecimal("0.01")),
                                             new Holding("B", new BigDecimal("500.00"), new BigDecimal("0.02")))),
                new Holding("C", new BigDecimal("250.00"), BigDecimal.ZERO)));
        assertEquals(new BigDecimal("1750.00"), Assets.value(fund));
        assertEquals(new BigDecimal("20.00"), Assets.fees(fund));
        assertEquals(3, Assets.countHoldings(fund));
    }
}
