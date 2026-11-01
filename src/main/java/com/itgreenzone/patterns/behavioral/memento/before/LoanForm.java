package com.itgreenzone.patterns.behavioral.memento.before;

import java.math.BigDecimal;

/**
 * BEFORE: to support "Back", the wizard must read and write every field through
 * public getters/setters. The form's internals are exposed to the outside.
 */
public class LoanForm {

    private String step = "APPLICANT";
    private String applicant = "";
    private BigDecimal amount = BigDecimal.ZERO.setScale(2);
    private int termMonths;
    private String purpose = "";          // added later by another developer

    public String getStep() { return step; }
    public void setStep(String step) { this.step = step; }
    public String getApplicant() { return applicant; }
    public void setApplicant(String applicant) { this.applicant = applicant; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public int getTermMonths() { return termMonths; }
    public void setTermMonths(int termMonths) { this.termMonths = termMonths; }
    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
}
