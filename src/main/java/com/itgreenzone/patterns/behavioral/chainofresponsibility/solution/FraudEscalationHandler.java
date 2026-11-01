package com.itgreenzone.patterns.behavioral.chainofresponsibility.solution;

import com.itgreenzone.patterns.behavioral.chainofresponsibility.Decision;
import com.itgreenzone.patterns.behavioral.chainofresponsibility.LoanApplication;
import com.itgreenzone.patterns.behavioral.chainofresponsibility.after.CreditCommittee;
import com.itgreenzone.patterns.behavioral.chainofresponsibility.after.LoanApprover;

/** Reference solution: a handler that short-circuits straight to the committee. */
public class FraudEscalationHandler extends LoanApprover {

    private final CreditCommittee committee;

    public FraudEscalationHandler(CreditCommittee committee) {
        this.committee = committee;
    }

    @Override
    protected boolean canHandle(LoanApplication app) {
        return app.fraudFlag();
    }

    @Override
    protected Decision decide(LoanApplication app) {
        return committee.approve(app);
    }
}
