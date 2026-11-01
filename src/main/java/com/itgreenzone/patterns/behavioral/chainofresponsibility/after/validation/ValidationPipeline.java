package com.itgreenzone.patterns.behavioral.chainofresponsibility.after.validation;

import com.itgreenzone.patterns.behavioral.chainofresponsibility.LoanApplication;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/** Runs EVERY check and collects ALL problems, so the customer can fix everything at once. */
public class ValidationPipeline {

    private final List<ApplicationCheck> checks;

    public ValidationPipeline(List<ApplicationCheck> checks) {
        this.checks = List.copyOf(checks);
    }

    public List<String> validate(LoanApplication app) {
        return checks.stream().map(c -> c.problem(app)).flatMap(Optional::stream).toList();
    }

    public static ApplicationCheck kyc() {
        return app -> app.kycComplete() ? Optional.empty() : Optional.of("KYC documents missing");
    }

    public static ApplicationCheck blacklist(Set<String> blacklisted) {
        return app -> blacklisted.contains(app.applicantId()) ? Optional.of("Applicant is blacklisted") : Optional.empty();
    }

    /** Instalment (amount / 120 months) must be at most 40% of monthly income. */
    public static ApplicationCheck income() {
        return app -> {
            BigDecimal instalment = app.amount().divide(new BigDecimal("120"), 2, RoundingMode.HALF_UP);
            BigDecimal max = app.monthlyIncome().multiply(new BigDecimal("0.40"));
            return instalment.compareTo(max) > 0 ? Optional.of("Instalment exceeds 40% of income") : Optional.empty();
        };
    }
}
