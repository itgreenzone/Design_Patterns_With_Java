package com.itgreenzone.patterns.creational.builder.after;

import java.math.BigDecimal;

/**
 * DIRECTOR (classic GoF form): knows the RECIPES for standard products,
 * so branch staff don't have to remember them.
 */
public class LoanApplicationDirector {

    /** Home loan preset: 25 years, mortgage insurance, collateral = the property. */
    public LoanApplication homeLoan(String applicant, BigDecimal amount, BigDecimal propertyValue) {
        return LoanApplication.builder(applicant, amount, 300)
                .collateral(propertyValue)
                .withInsurance()
                .build();
    }

    /** Car loan preset: 7 years, no collateral (amount stays under the threshold). */
    public LoanApplication carLoan(String applicant, BigDecimal amount) {
        return LoanApplication.builder(applicant, amount, 84)
                .promoCode("DRIVE2026")
                .build();
    }
}
