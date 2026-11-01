package com.itgreenzone.patterns.behavioral.visitor.after;

import java.math.BigDecimal;
import java.time.LocalDate;

/** CONCRETE ELEMENT. */
public final class MotorPolicy extends BasePolicy {

    private final int driverAge;

    public MotorPolicy(String number, BigDecimal sumInsured, LocalDate expiresOn, int driverAge) {
        super(number, sumInsured, expiresOn);
        this.driverAge = driverAge;
    }

    public int driverAge() { return driverAge; }

    /** Double dispatch, step 2: "this" is statically a MotorPolicy, so visitMotor is chosen. */
    @Override
    public <R> R accept(PolicyVisitor<R> visitor) {
        return visitor.visitMotor(this);
    }
}
