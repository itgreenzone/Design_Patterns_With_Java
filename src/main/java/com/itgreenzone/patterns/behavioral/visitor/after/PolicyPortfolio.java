package com.itgreenzone.patterns.behavioral.visitor.after;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** OBJECT STRUCTURE: holds the elements and runs a visitor over all of them. */
public class PolicyPortfolio {

    private final List<InsurancePolicy> policies = new ArrayList<>();

    public PolicyPortfolio add(InsurancePolicy policy) {
        policies.add(policy);
        return this;
    }

    public <R> List<R> applyToAll(PolicyVisitor<R> visitor) {
        return policies.stream().map(p -> p.accept(visitor)).toList();
    }

    public BigDecimal totalPremium() {
        return applyToAll(new PremiumCalculator()).stream().reduce(BigDecimal.ZERO.setScale(2), BigDecimal::add);
    }
}
