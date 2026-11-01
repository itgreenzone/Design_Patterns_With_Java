package com.itgreenzone.patterns.capstone.payment;

import java.math.BigDecimal;

/** STRATEGY: PayNow bank transfer, no fee. */
public class PayNowPayment implements PaymentMethod {

    @Override
    public String code() {
        return "PAYNOW";
    }

    @Override
    public BigDecimal fee(BigDecimal gross) {
        return Fees.percentPlusFixed(gross, "0", "0");
    }
}
