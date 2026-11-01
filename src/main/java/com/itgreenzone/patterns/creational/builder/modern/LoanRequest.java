package com.itgreenzone.patterns.creational.builder.modern;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * MODERN VARIANT: a record with a nested Builder.
 * The record gives immutability, equals/hashCode/toString for free; the compact
 * constructor validates; the Builder gives readable, named optional fields.
 * (Lombok's @Builder generates similar code, at the cost of a compile-time dependency.)
 */
public record LoanRequest(String applicant, BigDecimal amount, int tenureMonths,
                          Optional<String> coApplicant, Optional<BigDecimal> collateral) {

    public LoanRequest {
        if (tenureMonths < 12 || tenureMonths > 360) {
            throw new IllegalArgumentException("Tenure must be 12–360 months");
        }
        if (amount.compareTo(new BigDecimal("500000.00")) > 0 && collateral.isEmpty()) {
            throw new IllegalArgumentException("Collateral is required above SGD 500,000");
        }
    }

    public static Builder builder(String applicant, BigDecimal amount, int tenureMonths) {
        return new Builder(applicant, amount, tenureMonths);
    }

    public static final class Builder {
        private final String applicant;
        private final BigDecimal amount;
        private final int tenureMonths;
        private String coApplicant;
        private BigDecimal collateral;

        private Builder(String applicant, BigDecimal amount, int tenureMonths) {
            this.applicant = applicant;
            this.amount = amount;
            this.tenureMonths = tenureMonths;
        }

        public Builder coApplicant(String v) {
            coApplicant = v;
            return this;
        }

        public Builder collateral(BigDecimal v) {
            collateral = v;
            return this;
        }

        public LoanRequest build() {
            return new LoanRequest(applicant, amount, tenureMonths, Optional.ofNullable(coApplicant), Optional.ofNullable(collateral));
        }
    }
}
