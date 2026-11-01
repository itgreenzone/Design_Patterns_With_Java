package com.itgreenzone.patterns.capstone.payment;

import java.math.BigDecimal;

/** STRATEGY (L24): each payment method calculates its own fee. */
public interface PaymentMethod {

    String code();

    /** Fee charged on the gross amount, 2 dp HALF_UP. */
    BigDecimal fee(BigDecimal gross);
}
