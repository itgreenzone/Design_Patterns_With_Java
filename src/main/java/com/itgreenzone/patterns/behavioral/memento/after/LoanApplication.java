package com.itgreenzone.patterns.behavioral.memento.after;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * ORIGINATOR: the only class that can create a memento of its state and restore
 * from one. Snapshot has NO public accessors, so caretakers can store it but
 * never read or change what's inside.
 */
public class LoanApplication {

    private WizardStep step = WizardStep.APPLICANT;
    private String applicant = "";
    private BigDecimal amount = money("0");
    private int termMonths;
    private String purpose = "";

    public void enterApplicant(String name) {
        applicant = Objects.requireNonNull(name);
        step = WizardStep.LOAN_DETAILS;
    }

    public void enterLoan(BigDecimal amount, int termMonths, String purpose) {
        if (amount.signum() <= 0 || termMonths <= 0) {
            throw new IllegalArgumentException("amount and term must be positive");
        }
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
        this.termMonths = termMonths;
        this.purpose = purpose;
        step = WizardStep.REVIEW;
    }

    /** Simple instalment (no interest) for display: amount / term, 2 dp, HALF_UP. */
    public BigDecimal monthlyInstalment() {
        if (termMonths == 0) {
            return money("0");
        }
        return amount.divide(BigDecimal.valueOf(termMonths), 2, RoundingMode.HALF_UP);
    }

    /** Creates a MEMENTO. All fields are captured, including ones added later. */
    public Snapshot save() {
        return new Snapshot(step, applicant, amount, termMonths, purpose);
    }

    /** Restores from a MEMENTO. Only the originator can read its private fields. */
    public void restore(Snapshot s) {
        step = s.step;
        applicant = s.applicant;
        amount = s.amount;
        termMonths = s.termMonths;
        purpose = s.purpose;
    }

    public WizardStep step() { return step; }
    public String applicant() { return applicant; }
    public BigDecimal amount() { return amount; }
    public int termMonths() { return termMonths; }
    public String purpose() { return purpose; }

    private static BigDecimal money(String v) {
        return new BigDecimal(v).setScale(2, RoundingMode.HALF_UP);
    }

    /** MEMENTO: immutable, opaque to everyone except LoanApplication. */
    public static final class Snapshot {
        private final WizardStep step;
        private final String applicant;
        private final BigDecimal amount;
        private final int termMonths;
        private final String purpose;

        private Snapshot(WizardStep step, String applicant, BigDecimal amount, int termMonths, String purpose) {
            this.step = step;
            this.applicant = applicant;
            this.amount = amount;
            this.termMonths = termMonths;
            this.purpose = purpose;
        }
    }
}
