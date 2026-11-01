package com.itgreenzone.patterns.behavioral.chainofresponsibility.after;

import com.itgreenzone.patterns.behavioral.chainofresponsibility.Decision;
import com.itgreenzone.patterns.behavioral.chainofresponsibility.LoanApplication;

/** CONCRETE HANDLER at the END of the chain: handles everything that reaches it. */
public class CreditCommittee extends LoanApprover {

    @Override
    protected boolean canHandle(LoanApplication app) {
        return true;
    }

    @Override
    protected Decision decide(LoanApplication app) {
        return new Decision("Credit Committee", app.fraudFlag() ? "INVESTIGATE" : "APPROVED");
    }
}
