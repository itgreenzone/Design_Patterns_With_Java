package com.itgreenzone.patterns.behavioral.chainofresponsibility.after;

import com.itgreenzone.patterns.behavioral.chainofresponsibility.Decision;
import com.itgreenzone.patterns.behavioral.chainofresponsibility.LoanApplication;

import java.math.BigDecimal;

/**
 * ADDED LATER: high-risk applicants up to SGD 250,000 go to a Risk Officer, who
 * approves with conditions. Inserted at the FRONT of the chain; no other handler changed.
 */
public class RiskOfficer extends LoanApprover {

    @Override
    protected boolean canHandle(LoanApplication app) {
        return app.highRisk() && app.amount().compareTo(new BigDecimal("250000")) <= 0;
    }

    @Override
    protected Decision decide(LoanApplication app) {
        return new Decision("Risk Officer", "APPROVED WITH CONDITIONS");
    }
}
