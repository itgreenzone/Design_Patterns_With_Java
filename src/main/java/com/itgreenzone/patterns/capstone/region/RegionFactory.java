package com.itgreenzone.patterns.capstone.region;

import com.itgreenzone.patterns.capstone.processor.PaymentProcessor;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * ABSTRACT FACTORY (L03): creates a matching family of regional components:
 * currency, tax policy and payment gateway. forCode() is a static FACTORY METHOD.
 */
public interface RegionFactory {

    String currency();

    TaxPolicy taxPolicy();

    PaymentProcessor gateway();

    static RegionFactory forCode(String code) {
        return switch (code) {
            case "SG" -> new SingaporeRegion();
            case "LK" -> new SriLankaRegion();
            default -> throw new IllegalArgumentException("Unsupported region: " + code);
        };
    }

    /** net × percent / 100, 2 dp HALF_UP. */
    static TaxPolicy percentTax(String percent) {
        return net -> net.multiply(new BigDecimal(percent)).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
    }
}
