package com.itgreenzone.patterns.behavioral.visitor;

import com.itgreenzone.patterns.behavioral.visitor.after.*;
import com.itgreenzone.patterns.behavioral.visitor.before.RiskReportService;
import com.itgreenzone.patterns.behavioral.visitor.modern.Policy;
import com.itgreenzone.patterns.behavioral.visitor.modern.PolicyOperations;
import com.itgreenzone.patterns.behavioral.visitor.solution.RenewalReminderVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class VisitorTest {

    private static final LocalDate EXP = LocalDate.of(2026, 12, 1);

    private PolicyPortfolio portfolio;

    private static BigDecimal bd(String v) {
        return new BigDecimal(v);
    }

    @BeforeEach
    void setUp() {
        portfolio = new PolicyPortfolio()
                .add(new MotorPolicy("M-1", bd("40000"), EXP, 23))
                .add(new TravelPolicy("T-1", bd("50000"), EXP, 7, TravelPolicy.Region.ASIA))
                .add(new HealthPolicy("H-1", bd("100000"), EXP, 55, true));
    }

    @Test
    void premiumVisitorCalculatesPerPolicyType() {
        assertEquals(List.of(bd("1800.00"), bd("17.50"), bd("2730.00")), portfolio.applyToAll(new PremiumCalculator()));
        assertEquals(bd("4547.50"), portfolio.totalPremium());
    }

    @Test
    void otherPremiumRules() {
        PremiumCalculator calc = new PremiumCalculator();
        assertEquals(bd("900.00"), new MotorPolicy("M-2", bd("30000"), EXP, 40).accept(calc));
        assertEquals(bd("40.00"), new TravelPolicy("T-2", bd("50000"), EXP, 10, TravelPolicy.Region.WORLDWIDE).accept(calc));
        assertEquals(bd("1200.00"), new HealthPolicy("H-2", bd("80000"), EXP, 30, false).accept(calc));
    }

    @Test
    void riskAndRegulatoryVisitorsReuseTheSameStructure() {
        assertEquals(List.of("M-1 MOTOR HIGH", "T-1 TRAVEL LOW", "H-1 HEALTH HIGH"),
                portfolio.applyToAll(new RiskReportVisitor()));
        assertEquals("MOTOR|M-1|40000.00|2026-12-01|driverAge=23",
                portfolio.applyToAll(new RegulatoryExportVisitor()).get(0));
    }

    @Test
    void doubleDispatchPicksTheMethodFromTheRuntimeType() {
        InsurancePolicy declaredAsInterface = new HealthPolicy("H-9", bd("1000"), EXP, 30, false);
        String visited = declaredAsInterface.accept(new PolicyVisitor<String>() {
            public String visitMotor(MotorPolicy p) { return "motor"; }
            public String visitTravel(TravelPolicy p) { return "travel"; }
            public String visitHealth(HealthPolicy p) { return "health"; }
        });
        assertEquals("health", visited);
    }

    @Test
    void beforeInstanceofChainSilentlyForgetsAType() {
        var health = new com.itgreenzone.patterns.behavioral.visitor.before.HealthPolicy("H-1", bd("100000"), true);
        assertEquals("UNKNOWN", new RiskReportService().rating(health));
    }

    @Test
    void sealedRecordsGiveTheSameAnswersWithASwitch() {
        assertEquals(bd("1800.00"), PolicyOperations.premium(new Policy.Motor("M-1", bd("40000"), 23)));
        assertEquals(bd("40.00"), PolicyOperations.premium(new Policy.Travel("T-2", bd("50000"), 10, true)));
        assertEquals(bd("2730.00"), PolicyOperations.premium(new Policy.Health("H-1", bd("100000"), 55, true)));
        assertEquals("MEDIUM", PolicyOperations.riskRating(new Policy.Health("H-3", bd("1"), 52, false)));
        assertEquals("LOW", PolicyOperations.riskRating(new Policy.Travel("T-1", bd("1"), 7, false)));
    }

    @Test
    void renewalReminderSolution() {
        RenewalReminderVisitor v = new RenewalReminderVisitor(LocalDate.of(2026, 11, 1));
        List<Optional<String>> reminders = portfolio.applyToAll(v);
        assertEquals(Optional.of("Renew motor policy M-1 by 2026-12-01: bring your no-claims certificate"), reminders.get(0));
        assertEquals(Optional.empty(), reminders.get(1));
        assertEquals(Optional.of("Renew health policy H-1 by 2026-12-01: no new medical check needed"), reminders.get(2));

        RenewalReminderVisitor tooEarly = new RenewalReminderVisitor(LocalDate.of(2026, 10, 31));
        assertTrue(new MotorPolicy("M-5", bd("1"), EXP, 40).accept(tooEarly).isEmpty());
        RenewalReminderVisitor expired = new RenewalReminderVisitor(LocalDate.of(2026, 12, 2));
        assertTrue(new HealthPolicy("H-5", bd("1"), EXP, 40, false).accept(expired).isEmpty());
    }
}
