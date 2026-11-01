package com.itgreenzone.patterns.behavioral.visitor.solution;

import com.itgreenzone.patterns.behavioral.visitor.after.*;

import java.time.LocalDate;
import java.util.Optional;

/** Reference solution: a fourth operation, and not one policy class edited. */
public class RenewalReminderVisitor implements PolicyVisitor<Optional<String>> {

    private static final int WINDOW_DAYS = 30;

    private final LocalDate today;

    public RenewalReminderVisitor(LocalDate today) {
        this.today = today;
    }

    @Override
    public Optional<String> visitMotor(MotorPolicy p) {
        return due(p) ? Optional.of("Renew motor policy " + p.number() + " by " + p.expiresOn()
                + ": bring your no-claims certificate") : Optional.empty();
    }

    @Override
    public Optional<String> visitTravel(TravelPolicy p) {
        return Optional.empty();
    }

    @Override
    public Optional<String> visitHealth(HealthPolicy p) {
        return due(p) ? Optional.of("Renew health policy " + p.number() + " by " + p.expiresOn()
                + ": no new medical check needed") : Optional.empty();
    }

    private boolean due(InsurancePolicy p) {
        return !p.expiresOn().isBefore(today) && !p.expiresOn().isAfter(today.plusDays(WINDOW_DAYS));
    }
}
