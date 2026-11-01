package com.itgreenzone.patterns.foundations.solid;

import com.itgreenzone.patterns.foundations.solid.dip.after.ClaimNotificationService;
import com.itgreenzone.patterns.foundations.solid.isp.after.ClaimsDashboard;
import com.itgreenzone.patterns.foundations.solid.isp.after.InMemoryClaimStore;
import com.itgreenzone.patterns.foundations.solid.lsp.after.ApprovalBatch;
import com.itgreenzone.patterns.foundations.solid.lsp.after.OpenClaim;
import com.itgreenzone.patterns.foundations.solid.ocp.after.HealthPayoutRule;
import com.itgreenzone.patterns.foundations.solid.ocp.after.MotorPayoutRule;
import com.itgreenzone.patterns.foundations.solid.ocp.after.PayoutRule;
import com.itgreenzone.patterns.foundations.solid.ocp.after.TravelPayoutRule;
import com.itgreenzone.patterns.foundations.solid.srp.after.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SolidTest {

    static Claim claim(ClaimType type, String amount) {
        return new Claim("C-1", type, new BigDecimal(amount), "alice@example.com");
    }

    @Nested
    class Srp {
        @Test
        void serviceCoordinatesSingleResponsibilityClasses() {
            ClaimNotifier notifier = new ClaimNotifier();
            ClaimService service = new ClaimService(new ClaimValidator(), new PayoutCalculator(), notifier);
            assertEquals(new BigDecimal("900.00"), service.process(claim(ClaimType.HEALTH, "1000.00")));
            assertEquals(1, notifier.outbox().size());
        }

        @Test
        void validatorRejectsZeroAmount() {
            assertThrows(IllegalArgumentException.class, () -> new ClaimValidator().validate(claim(ClaimType.MOTOR, "0")));
        }
    }

    @Nested
    class Ocp {
        private final com.itgreenzone.patterns.foundations.solid.ocp.after.PayoutCalculator calc =
                new com.itgreenzone.patterns.foundations.solid.ocp.after.PayoutCalculator(
                        List.of(new MotorPayoutRule(), new TravelPayoutRule(), new HealthPayoutRule()));

        @Test
        void eachRuleAppliesItsOwnMaths() {
            assertEquals(new BigDecimal("1500.00"), calc.payout(claim(ClaimType.MOTOR, "2000.00")));
            assertEquals(new BigDecimal("5000.00"), calc.payout(claim(ClaimType.TRAVEL, "8000.00")));
            assertEquals(new BigDecimal("900.00"), calc.payout(claim(ClaimType.HEALTH, "1000.00")));
        }

        @Test
        void newRuleNeedsNoChangeToCalculator() {
            PayoutRule promo = new PayoutRule() {
                public boolean supports(Claim c) { return c.type() == ClaimType.TRAVEL; }
                public BigDecimal payout(Claim c) { return c.amount(); }
            };
            var promoCalc = new com.itgreenzone.patterns.foundations.solid.ocp.after.PayoutCalculator(List.of(promo));
            assertEquals(new BigDecimal("8000.00"), promoCalc.payout(claim(ClaimType.TRAVEL, "8000.00")));
        }

        @Test
        void beforeAndAfterGiveSameAnswers() {
            var before = new com.itgreenzone.patterns.foundations.solid.ocp.before.PayoutCalculator();
            for (ClaimType t : ClaimType.values()) {
                Claim c = claim(t, "2000.00");
                assertEquals(before.payout(c), calc.payout(c));
            }
        }
    }

    @Nested
    class Lsp {
        @Test
        void beforeVersionBreaksAtRuntime() {
            var batch = new com.itgreenzone.patterns.foundations.solid.lsp.before.ApprovalBatch();
            var files = List.of(new com.itgreenzone.patterns.foundations.solid.lsp.before.ClaimFile("A"),
                    new com.itgreenzone.patterns.foundations.solid.lsp.before.ReadOnlyClaimFile("B"));
            assertThrows(UnsupportedOperationException.class, () -> batch.approveAll(files));
        }

        @Test
        void afterVersionApprovesEverythingItIsGiven() {
            OpenClaim a = new OpenClaim("A"), b = new OpenClaim("B");
            new ApprovalBatch().approveAll(List.of(a, b));
            assertEquals("APPROVED", a.status());
            assertEquals("APPROVED", b.status());
        }
    }

    @Nested
    class Isp {
        @Test
        void dashboardOnlyNeedsAReader() {
            InMemoryClaimStore store = new InMemoryClaimStore();
            store.save(claim(ClaimType.MOTOR, "100.00"));
            store.save(new Claim("C-2", ClaimType.TRAVEL, new BigDecimal("50.50"), "b@example.com"));
            assertEquals(new BigDecimal("150.50"), new ClaimsDashboard(store).totalClaimed());
        }
    }

    @Nested
    class Dip {
        @Test
        void fakeSenderMakesTheServiceTestable() {
            List<String> sent = new ArrayList<>();
            new ClaimNotificationService((to, body) -> sent.add(to + "|" + body)).claimReceived(claim(ClaimType.MOTOR, "10.00"));
            assertEquals(List.of("alice@example.com|We received claim C-1"), sent);
        }
    }
}
