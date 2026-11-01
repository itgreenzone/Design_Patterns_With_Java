package com.itgreenzone.patterns.creational.builder.after;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

/**
 * AFTER: PRODUCT – immutable, always valid. The only way to get one is through the Builder.
 */
public final class LoanApplication {

    private static final BigDecimal COLLATERAL_THRESHOLD = new BigDecimal("500000.00");

    private final String applicant;
    private final BigDecimal amount;
    private final int tenureMonths;
    private final String coApplicant;
    private final BigDecimal collateral;
    private final String employer;
    private final String promoCode;
    private final boolean insuranceAddOn;
    private final String disbursementAccount;

    private LoanApplication(Builder b) {                 // private: only the Builder can call it
        this.applicant = b.applicant;
        this.amount = b.amount;
        this.tenureMonths = b.tenureMonths;
        this.coApplicant = b.coApplicant;
        this.collateral = b.collateral;
        this.employer = b.employer;
        this.promoCode = b.promoCode;
        this.insuranceAddOn = b.insuranceAddOn;
        this.disbursementAccount = b.disbursementAccount;
    }

    public static Builder builder(String applicant, BigDecimal amount, int tenureMonths) {
        return new Builder(applicant, amount, tenureMonths);
    }

    /** BUILDER: required fields in the constructor, optional ones as fluent methods. */
    public static final class Builder {
        private final String applicant;
        private final BigDecimal amount;
        private final int tenureMonths;
        private String coApplicant;
        private BigDecimal collateral;
        private String employer;
        private String promoCode;
        private boolean insuranceAddOn;
        private String disbursementAccount;

        private Builder(String applicant, BigDecimal amount, int tenureMonths) {
            this.applicant = Objects.requireNonNull(applicant, "applicant");
            this.amount = Objects.requireNonNull(amount, "amount");
            this.tenureMonths = tenureMonths;
        }

        public Builder coApplicant(String name) {
            this.coApplicant = name;
            return this;
        }

        public Builder collateral(BigDecimal value) {
            this.collateral = value;
            return this;
        }

        public Builder employer(String employer) {
            this.employer = employer;
            return this;
        }

        public Builder promoCode(String code) {
            this.promoCode = code;
            return this;
        }

        public Builder withInsurance() {
            this.insuranceAddOn = true;
            return this;
        }

        public Builder disbursementAccount(String account) {
            this.disbursementAccount = account;
            return this;
        }

        /** All cross-field rules live here, in ONE place. */
        public LoanApplication build() {
            if (amount.signum() <= 0) {
                throw new IllegalStateException("Amount must be positive");
            }
            if (tenureMonths < 12 || tenureMonths > 360) {
                throw new IllegalStateException("Tenure must be 12–360 months");
            }
            if (amount.compareTo(COLLATERAL_THRESHOLD) > 0 && collateral == null) {
                throw new IllegalStateException("Collateral is required above SGD 500,000");
            }
            return new LoanApplication(this);
        }
    }

    public String applicant() { return applicant; }
    public BigDecimal amount() { return amount; }
    public int tenureMonths() { return tenureMonths; }
    public Optional<String> coApplicant() { return Optional.ofNullable(coApplicant); }
    public Optional<BigDecimal> collateral() { return Optional.ofNullable(collateral); }
    public Optional<String> employer() { return Optional.ofNullable(employer); }
    public Optional<String> promoCode() { return Optional.ofNullable(promoCode); }
    public boolean insuranceAddOn() { return insuranceAddOn; }
    public Optional<String> disbursementAccount() { return Optional.ofNullable(disbursementAccount); }
}
