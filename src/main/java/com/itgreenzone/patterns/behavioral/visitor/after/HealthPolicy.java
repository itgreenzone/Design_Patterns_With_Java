package com.itgreenzone.patterns.behavioral.visitor.after;

import java.math.BigDecimal;
import java.time.LocalDate;

/** CONCRETE ELEMENT. */
public final class HealthPolicy extends BasePolicy {

    private final int insuredAge;
    private final boolean smoker;

    public HealthPolicy(String number, BigDecimal sumInsured, LocalDate expiresOn, int insuredAge, boolean smoker) {
        super(number, sumInsured, expiresOn);
        this.insuredAge = insuredAge;
        this.smoker = smoker;
    }

    public int insuredAge() { return insuredAge; }
    public boolean smoker() { return smoker; }

    @Override
    public <R> R accept(PolicyVisitor<R> visitor) {
        return visitor.visitHealth(this);
    }
}
