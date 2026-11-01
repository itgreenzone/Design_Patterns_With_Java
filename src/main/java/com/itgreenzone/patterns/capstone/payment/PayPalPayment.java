package com.itgreenzone.patterns.capstone.payment;

import java.math.BigDecimal;

/** STRATEGY: PayPal, 3.4% + 0.50. */
public class PayPalPayment implements PaymentMethod {

    @Override
    public String code() {
        return "PAYPAL";
    }

    @Override
    public BigDecimal fee(BigDecimal gross) {
        return Fees.percentPlusFixed(gross, "3.4", "0.50");
    }
}
