package com.itgreenzone.patterns.behavioral.chainofresponsibility.exercise;

import com.itgreenzone.patterns.behavioral.chainofresponsibility.Decision;
import com.itgreenzone.patterns.behavioral.chainofresponsibility.LoanApplication;
import com.itgreenzone.patterns.behavioral.chainofresponsibility.after.CreditCommittee;
import com.itgreenzone.patterns.behavioral.chainofresponsibility.after.LoanApprover;

/**
 * HANDS-ON EXERCISE (≈20 minutes)
 *
 * Applications with a FRAUD FLAG must skip every normal approver and go STRAIGHT
 * to the Credit Committee, whatever the amount.
 *  - canHandle(): only fraud-flagged applications
 *  - decide(): delegate to the committee given in the constructor
 * Put it at the FRONT of ApprovalChains.withRiskReview(). No other class may change.
 * Reference solution: ..chainofresponsibility.solution.FraudEscalationHandler
 */
public class FraudEscalationHandler extends LoanApprover {

    public FraudEscalationHandler(CreditCommittee committee) {
        // TODO
    }

    @Override
    protected boolean canHandle(LoanApplication app) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    protected Decision decide(LoanApplication app) {
        throw new UnsupportedOperationException("TODO");
    }
}
