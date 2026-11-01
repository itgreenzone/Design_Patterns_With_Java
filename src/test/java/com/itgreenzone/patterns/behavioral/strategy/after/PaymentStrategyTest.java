package com.itgreenzone.patterns.behavioral.strategy.after;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PaymentStrategyTest {

    private static final BigDecimal AMOUNT = new BigDecimal("120.00");

    @Nested
    @DisplayName("Each concrete strategy calculates its own fee")
    class Fees {

        @Test
        void creditCardChargesTwoPointFivePercent() {
            PaymentReceipt r = new CreditCardPayment("4111111111111111").pay(AMOUNT);
            assertEquals("CREDIT_CARD", r.method());
            assertEquals(new BigDecimal("3.00"), r.fee());
            assertEquals(new BigDecimal("123.00"), r.total());
            assertTrue(r.reference().startsWith("CC-"));
        }

        @Test
        void payPalChargesPercentPlusFixedFee() {
            PaymentReceipt r = new PayPalPayment("alice@example.com").pay(AMOUNT);
            assertEquals(new BigDecimal("4.58"), r.fee()); // 4.08 + 0.50
            assertTrue(r.reference().startsWith("PP-"));
        }

        @Test
        void grabPayChargesOnePointFivePercent() {
            PaymentReceipt r = new GrabPayPayment("+6591234567").pay(AMOUNT);
            assertEquals(new BigDecimal("1.80"), r.fee());
            assertTrue(r.reference().startsWith("GP-"));
        }

        @Test
        void payNowIsFree() {
            PaymentReceipt r = new PayNowPayment("+6591234567").pay(AMOUNT);
            assertEquals(new BigDecimal("0.00"), r.fee());
            assertEquals(AMOUNT, r.total());
        }
    }

    @Nested
    @DisplayName("Each strategy validates its own input")
    class Validation {

        @ParameterizedTest
        @ValueSource(strings = {"", "1234", "411111111111111X", "41111111111111111"})
        void rejectsBadCardNumbers(String card) {
            assertThrows(IllegalArgumentException.class, () -> new CreditCardPayment(card));
        }

        @Test
        void rejectsBadPayPalEmail() {
            assertThrows(IllegalArgumentException.class, () -> new PayPalPayment("not-an-email"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"91234567", "+6571234567", "+659123456"})
        void rejectsNonSingaporeMobile(String mobile) {
            assertThrows(IllegalArgumentException.class, () -> new GrabPayPayment(mobile));
        }

        @Test
        void rejectsBlankPayNowId() {
            assertThrows(IllegalArgumentException.class, () -> new PayNowPayment("  "));
        }
    }

    @Nested
    @DisplayName("PaymentService (Context)")
    class Context {

        @Test
        void delegatesToTheCurrentStrategy() {
            PaymentService service = new PaymentService(new GrabPayPayment("+6591234567"));
            assertEquals("GRABPAY", service.checkout(AMOUNT).method());
        }

        @Test
        void strategyCanBeSwappedAtRuntime() {
            PaymentService service = new PaymentService(new CreditCardPayment("4111111111111111"));
            assertEquals("CREDIT_CARD", service.checkout(AMOUNT).method());

            service.setPaymentStrategy(new PayPalPayment("alice@example.com"));
            assertEquals("PAYPAL", service.checkout(AMOUNT).method());
        }

        @Test
        void acceptsAnyNewStrategyWithoutChange() {
            // Open/Closed: a strategy the Context has never heard of still works.
            PaymentStrategy voucher = new PaymentStrategy() {
                public String name() { return "VOUCHER"; }
                public PaymentReceipt pay(BigDecimal amount) {
                    return new PaymentReceipt(name(), amount, BigDecimal.ZERO, "VC-1");
                }
            };
            PaymentService service = new PaymentService(voucher);
            assertEquals("VC-1", service.checkout(AMOUNT).reference());
        }

        @Test
        void rejectsNullStrategy() {
            assertThrows(NullPointerException.class, () -> new PaymentService(null));
        }

        @ParameterizedTest
        @ValueSource(strings = {"0", "-5.00"})
        void rejectsNonPositiveAmounts(String amount) {
            PaymentService service = new PaymentService(new PayNowPayment("+6591234567"));
            assertThrows(IllegalArgumentException.class, () -> service.checkout(new BigDecimal(amount)));
        }
    }
}
