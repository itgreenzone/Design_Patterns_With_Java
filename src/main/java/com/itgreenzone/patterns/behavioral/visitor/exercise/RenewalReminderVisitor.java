package com.itgreenzone.patterns.behavioral.visitor.exercise;

import com.itgreenzone.patterns.behavioral.visitor.after.*;

import java.time.LocalDate;
import java.util.Optional;

/**
 * HANDS-ON EXERCISE (≈20 minutes)
 *
 * Add a new OPERATION without touching any policy class. Return a reminder only
 * when the policy expires within the next 30 days (today ≤ expiresOn ≤ today + 30):
 *   Motor:  "Renew motor policy <no> by <date>: bring your no-claims certificate"
 *   Travel: Optional.empty() always (single-trip cover isn't renewed)
 *   Health: "Renew health policy <no> by <date>: no new medical check needed"
 * Reference: ..visitor.solution.RenewalReminderVisitor
 */
public class RenewalReminderVisitor implements PolicyVisitor<Optional<String>> {

    private final LocalDate today;

    public RenewalReminderVisitor(LocalDate today) {
        this.today = today;
    }

    @Override
    public Optional<String> visitMotor(MotorPolicy policy) {
        throw new UnsupportedOperationException("TODO (today = " + today + ")");
    }

    @Override
    public Optional<String> visitTravel(TravelPolicy policy) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public Optional<String> visitHealth(HealthPolicy policy) {
        throw new UnsupportedOperationException("TODO");
    }
}
