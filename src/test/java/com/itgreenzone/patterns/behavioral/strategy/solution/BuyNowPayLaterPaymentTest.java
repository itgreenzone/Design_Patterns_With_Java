package com.itgreenzone.patterns.behavioral.strategy.solution;

import com.itgreenzone.patterns.behavioral.strategy.after.PaymentReceipt;
import com.itgreenzone.patterns.behavioral.strategy.after.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BuyNowPayLaterPaymentTest {

    private final BuyNowPayLaterPayment bnpl = new BuyNowPayLaterPayment("ACC-1001");

    @Test
    void worksWithUnchangedPaymentService() {
        PaymentReceipt r = new PaymentService(bnpl).checkout(new BigDecimal("300.00"));
        assertEquals("BNPL", r.method());
        assertEquals(new BigDecimal("0.00"), r.fee());
        assertTrue(r.reference().startsWith("BNPL-"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"50.00", "3000.00"})
    void acceptsBoundaryAmounts(String amount) {
        assertDoesNotThrow(() -> bnpl.pay(new BigDecimal(amount)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"49.99", "3000.01"})
    void rejectsAmountsOutsideRange(String amount) {
        assertThrows(IllegalArgumentException.class, () -> bnpl.pay(new BigDecimal(amount)));
    }

    @Test
    void splitsIntoThreeInstalments() {
        assertEquals(new BigDecimal("33.33"), bnpl.installment(new BigDecimal("100.00")));
    }

    @Test
    void rejectsBlankAccount() {
        assertThrows(IllegalArgumentException.class, () -> new BuyNowPayLaterPayment(" "));
    }
}
