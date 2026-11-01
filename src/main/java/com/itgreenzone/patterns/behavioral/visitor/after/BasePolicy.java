package com.itgreenzone.patterns.behavioral.visitor.after;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

/** Shared fields for the concrete elements. */
public abstract class BasePolicy implements InsurancePolicy {

    private final String number;
    private final BigDecimal sumInsured;
    private final LocalDate expiresOn;

    protected BasePolicy(String number, BigDecimal sumInsured, LocalDate expiresOn) {
        this.number = Objects.requireNonNull(number);
        this.sumInsured = sumInsured.setScale(2, RoundingMode.HALF_UP);
        this.expiresOn = Objects.requireNonNull(expiresOn);
    }

    @Override public String number() { return number; }
    @Override public BigDecimal sumInsured() { return sumInsured; }
    @Override public LocalDate expiresOn() { return expiresOn; }
}
