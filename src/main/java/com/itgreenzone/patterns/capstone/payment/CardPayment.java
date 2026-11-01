package com.itgreenzone.patterns.capstone.payment;

import java.math.BigDecimal;

/** STRATEGY: card, 2.9% + 0.30. */
public class CardPayment implements PaymentMethod {

    @Override
    public String code() {
        return "CARD";
    }

    @Override
    public BigDecimal fee(BigDecimal gross) {
        return Fees.percentPlusFixed(gross, "2.9", "0.30");
    }
}
