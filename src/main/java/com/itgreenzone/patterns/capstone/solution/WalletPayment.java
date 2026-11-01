package com.itgreenzone.patterns.capstone.solution;

import com.itgreenzone.patterns.capstone.payment.Fees;
import com.itgreenzone.patterns.capstone.payment.PaymentMethod;

import java.math.BigDecimal;

/** Reference solution. Strategy made it easy: CheckoutFacade did not change. */
public class WalletPayment implements PaymentMethod {

    @Override
    public String code() {
        return "WALLET";
    }

    @Override
    public BigDecimal fee(BigDecimal gross) {
        return Fees.percentPlusFixed(gross, "1.5", "0");
    }
}
