package com.itgreenzone.patterns.behavioral.chainofresponsibility;

import java.math.BigDecimal;

public record LoanApplication(String applicantId, BigDecimal amount, BigDecimal monthlyIncome,
                              boolean kycComplete, boolean highRisk, boolean fraudFlag) {

    public static LoanApplication of(String id, String amount) {
        return new LoanApplication(id, new BigDecimal(amount), new BigDecimal("20000.00"), true, false, false);
    }

    public LoanApplication asHighRisk() {
        return new LoanApplication(applicantId, amount, monthlyIncome, kycComplete, true, fraudFlag);
    }

    public LoanApplication withFraudFlag() {
        return new LoanApplication(applicantId, amount, monthlyIncome, kycComplete, highRisk, true);
    }
}
