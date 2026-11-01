package com.itgreenzone.patterns.behavioral.memento.modern;

import com.itgreenzone.patterns.behavioral.memento.after.WizardStep;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * MODERN VARIANT: an immutable record IS a safe snapshot. Sharing it can't
 * corrupt the originator, so no defensive copy is needed. The trade-off: record
 * accessors are public, so the caretaker CAN read the state (weaker encapsulation).
 */
public record FormState(WizardStep step, String applicant, BigDecimal amount, int termMonths) {

    public static FormState empty() {
        return new FormState(WizardStep.APPLICANT, "", BigDecimal.ZERO.setScale(2), 0);
    }

    public FormState withApplicant(String name) {
        return new FormState(WizardStep.LOAN_DETAILS, name, amount, termMonths);
    }

    public FormState withLoan(BigDecimal value, int term) {
        return new FormState(WizardStep.REVIEW, applicant, value.setScale(2, RoundingMode.HALF_UP), term);
    }
}
