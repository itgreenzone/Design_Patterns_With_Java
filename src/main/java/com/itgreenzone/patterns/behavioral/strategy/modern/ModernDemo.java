package com.itgreenzone.patterns.behavioral.strategy.modern;

import java.math.BigDecimal;
import java.util.List;

/** Run me to see the lambda-based Strategy variant. */
public class ModernDemo {

    public static void main(String[] args) {
        CheckoutService checkout = new CheckoutService();
        BigDecimal amount = new BigDecimal("120.00");

        // A brand-new strategy defined inline: a promo with a flat SGD 1 fee.
        PaymentMethod promo = new PaymentMethod("PROMO_WALLET", a -> new BigDecimal("1.00"));

        List.of(PaymentMethod.CREDIT_CARD, PaymentMethod.PAYPAL,
                PaymentMethod.GRABPAY, PaymentMethod.PAYNOW, promo)
            .forEach(m -> System.out.println(checkout.checkout(amount, m)));
    }
}
