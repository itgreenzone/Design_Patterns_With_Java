package com.itgreenzone.patterns.capstone.exercise;

import com.itgreenzone.patterns.capstone.payment.PaymentMethod;

import java.math.BigDecimal;

/**
 * CAPSTONE EXERCISE, part 1 (≈15 minutes): a new payment method.
 * An e-wallet with code "WALLET" and a fee of 1.5% (no fixed part), 2 dp HALF_UP.
 * Hint: Fees.percentPlusFixed(gross, "1.5", "0").
 * Then write down: which pattern made this change easy, and which classes did NOT change?
 * Reference: ..capstone.solution.WalletPayment
 */
public class WalletPayment implements PaymentMethod {

    @Override
    public String code() {
        return "WALLET";
    }

    @Override
    public BigDecimal fee(BigDecimal gross) {
        throw new UnsupportedOperationException("TODO");
    }
}
