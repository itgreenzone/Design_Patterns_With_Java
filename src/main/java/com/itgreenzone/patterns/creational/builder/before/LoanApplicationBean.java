package com.itgreenzone.patterns.creational.builder.before;

import java.math.BigDecimal;

/**
 * BEFORE (2): JAVABEAN WITH SETTERS.
 * Readable, but the object is MUTABLE and can be HALF-BUILT: nothing stops a
 * caller from submitting it before setAmount() is called, and no rule is checked.
 */
public class LoanApplicationBean {

    private String applicant;
    private BigDecimal amount;
    private int tenureMonths;
    private BigDecimal collateral;

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setTenureMonths(int tenureMonths) {
        this.tenureMonths = tenureMonths;
    }

    public void setCollateral(BigDecimal collateral) {
        this.collateral = collateral;
    }

    public String getApplicant() {
        return applicant;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public int getTenureMonths() {
        return tenureMonths;
    }

    public BigDecimal getCollateral() {
        return collateral;
    }
}
