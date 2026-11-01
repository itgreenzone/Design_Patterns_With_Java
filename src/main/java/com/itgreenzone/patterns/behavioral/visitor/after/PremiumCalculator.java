package com.itgreenzone.patterns.behavioral.visitor.after;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * CONCRETE VISITOR: annual premium.
 *   Motor:  3% of sum insured, ×1.5 for drivers under 25
 *   Travel: per day, 2.50 (ASIA) or 4.00 (WORLDWIDE)
 *   Health: 1.5% of sum insured, ×1.4 smoker, ×1.3 aged 50+
 */
public class PremiumCalculator implements PolicyVisitor<BigDecimal> {

    @Override
    public BigDecimal visitMotor(MotorPolicy p) {
        BigDecimal premium = p.sumInsured().multiply(new BigDecimal("0.03"));
        if (p.driverAge() < 25) {
            premium = premium.multiply(new BigDecimal("1.5"));
        }
        return money(premium);
    }

    @Override
    public BigDecimal visitTravel(TravelPolicy p) {
        BigDecimal daily = p.region() == TravelPolicy.Region.ASIA ? new BigDecimal("2.50") : new BigDecimal("4.00");
        return money(daily.multiply(BigDecimal.valueOf(p.tripDays())));
    }

    @Override
    public BigDecimal visitHealth(HealthPolicy p) {
        BigDecimal premium = p.sumInsured().multiply(new BigDecimal("0.015"));
        if (p.smoker()) {
            premium = premium.multiply(new BigDecimal("1.4"));
        }
        if (p.insuredAge() >= 50) {
            premium = premium.multiply(new BigDecimal("1.3"));
        }
        return money(premium);
    }

    private static BigDecimal money(BigDecimal v) {
        return v.setScale(2, RoundingMode.HALF_UP);
    }
}
