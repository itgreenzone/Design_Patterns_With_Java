package com.itgreenzone.patterns.capstone.payment;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Small helper so every strategy rounds the same way. */
public final class Fees {

    private Fees() {
    }

    /** gross × percent / 100 + fixed, 2 dp HALF_UP. */
    public static BigDecimal percentPlusFixed(BigDecimal gross, String percent, String fixed) {
        return gross.multiply(new BigDecimal(percent)).divide(new BigDecimal("100"))
                .add(new BigDecimal(fixed)).setScale(2, RoundingMode.HALF_UP);
    }
}
