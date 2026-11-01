package com.itgreenzone.patterns.creational.builder.solution;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Reference solution. */
public final class InsuranceQuoteRequest {

    private final String customer;
    private final int age;
    private final BigDecimal sumInsured;
    private final boolean smoker;
    private final List<String> riders;

    private InsuranceQuoteRequest(Builder b) {
        this.customer = b.customer;
        this.age = b.age;
        this.sumInsured = b.sumInsured;
        this.smoker = b.smoker;
        this.riders = List.copyOf(b.riders);          // defensive, unmodifiable copy
    }

    public static Builder builder(String customer, int age) {
        return new Builder(customer, age);
    }

    public static final class Builder {
        private final String customer;
        private final int age;
        private BigDecimal sumInsured = new BigDecimal("100000.00");
        private boolean smoker;
        private final List<String> riders = new ArrayList<>();

        private Builder(String customer, int age) {
            this.customer = Objects.requireNonNull(customer, "customer");
            this.age = age;
        }

        public Builder sumInsured(BigDecimal value) {
            this.sumInsured = Objects.requireNonNull(value);
            return this;
        }

        public Builder smoker() {
            this.smoker = true;
            return this;
        }

        public Builder rider(String rider) {
            this.riders.add(rider);
            return this;
        }

        public InsuranceQuoteRequest build() {
            if (age < 18 || age > 75) {
                throw new IllegalStateException("Age must be 18–75");
            }
            if (sumInsured.compareTo(new BigDecimal("10000.00")) < 0 || sumInsured.compareTo(new BigDecimal("2000000.00")) > 0) {
                throw new IllegalStateException("Sum insured must be SGD 10,000 – 2,000,000");
            }
            return new InsuranceQuoteRequest(this);
        }
    }

    public String customer() { return customer; }
    public int age() { return age; }
    public BigDecimal sumInsured() { return sumInsured; }
    public boolean smoker() { return smoker; }
    public List<String> riders() { return riders; }
}
