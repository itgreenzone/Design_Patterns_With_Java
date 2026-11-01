package com.itgreenzone.patterns.behavioral.chainofresponsibility.after;

/** CLIENT-side assembly: the ONLY place that knows the order of the chain. */
public final class ApprovalChains {

    private ApprovalChains() {
    }

    public static LoanApprover standard() {
        LoanApprover officer = new LimitApprover("Loan Officer", "50000");
        officer.setNext(new LimitApprover("Branch Manager", "250000"))
               .setNext(new LimitApprover("Regional Director", "1000000"))
               .setNext(new CreditCommittee());
        return officer;
    }

    public static LoanApprover withRiskReview() {
        LoanApprover risk = new RiskOfficer();
        risk.setNext(standard());
        return risk;
    }
}
