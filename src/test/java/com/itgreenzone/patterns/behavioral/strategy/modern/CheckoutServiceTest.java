package com.itgreenzone.patterns.behavioral.strategy.modern;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutServiceTest {

    private final CheckoutService checkout = new CheckoutService();
    private static final BigDecimal AMOUNT = new BigDecimal("120.00");

    @Test
    void builtInMethodsMatchTheClassicVersion() {
        assertEquals(new BigDecimal("3.00"), checkout.checkout(AMOUNT, PaymentMethod.CREDIT_CARD).fee());
        assertEquals(new BigDecimal("4.58"), checkout.checkout(AMOUNT, PaymentMethod.PAYPAL).fee());
        assertEquals(new BigDecimal("1.80"), checkout.checkout(AMOUNT, PaymentMethod.GRABPAY).fee());
        assertEquals(new BigDecimal("0.00"), checkout.checkout(AMOUNT, PaymentMethod.PAYNOW).fee());
    }

    @Test
    void newStrategyCanBeALambda() {
        PaymentMethod promo = new PaymentMethod("PROMO", a -> new BigDecimal("1.00"));
        Receipt r = checkout.checkout(AMOUNT, promo);
        assertEquals(new BigDecimal("121.00"), r.total());
    }

    @Test
    void strategiesCompose() {
        FeeStrategy fee = FeeStrategy.percent("0.01").plus("2.00");
        assertEquals(new BigDecimal("3.20"), fee.feeFor(AMOUNT));
    }

    @Test
    void rejectsNonPositiveAmount() {
        assertThrows(IllegalArgumentException.class, () -> checkout.checkout(BigDecimal.ZERO, PaymentMethod.PAYNOW));
    }
}
