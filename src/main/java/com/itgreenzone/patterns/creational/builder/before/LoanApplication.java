package com.itgreenzone.patterns.creational.builder.before;

import java.math.BigDecimal;

/**
 * BEFORE (1): TELESCOPING CONSTRUCTORS.
 * Which argument is which? new LoanApplication("Alice", amt, 300, null, coll, "DBS", null, true, "123")
 * Swap two Strings and it still compiles. Every new optional field adds another constructor.
 */
public class LoanApplication {

    private final String applicant;
    private final BigDecimal amount;
    private final int tenureMonths;
    private final String coApplicant;
    private final BigDecimal collateral;
    private final String employer;
    private final String promoCode;
    private final boolean insuranceAddOn;
    private final String disbursementAccount;

    public LoanApplication(String applicant, BigDecimal amount, int tenureMonths) {
        this(applicant, amount, tenureMonths, null, null);
    }

    public LoanApplication(String applicant, BigDecimal amount, int tenureMonths, String coApplicant, BigDecimal collateral) {
        this(applicant, amount, tenureMonths, coApplicant, collateral, null, null, false, null);
    }

    public LoanApplication(String applicant, BigDecimal amount, int tenureMonths, String coApplicant,
                           BigDecimal collateral, String employer, String promoCode,
                           boolean insuranceAddOn, String disbursementAccount) {
        this.applicant = applicant;
        this.amount = amount;
        this.tenureMonths = tenureMonths;
        this.coApplicant = coApplicant;
        this.collateral = collateral;
        this.employer = employer;
        this.promoCode = promoCode;
        this.insuranceAddOn = insuranceAddOn;
        this.disbursementAccount = disbursementAccount;
    }

    public String employer() {
        return employer;
    }

    public String promoCode() {
        return promoCode;
    }

    public BigDecimal amount() {
        return amount;
    }

    public String applicant() {
        return applicant;
    }

    public int tenureMonths() {
        return tenureMonths;
    }

    public String coApplicant() {
        return coApplicant;
    }

    public BigDecimal collateral() {
        return collateral;
    }

    public boolean insuranceAddOn() {
        return insuranceAddOn;
    }

    public String disbursementAccount() {
        return disbursementAccount;
    }
}
