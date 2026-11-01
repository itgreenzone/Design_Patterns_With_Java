package com.itgreenzone.patterns.behavioral.chainofresponsibility.after;

import com.itgreenzone.patterns.behavioral.chainofresponsibility.Decision;
import com.itgreenzone.patterns.behavioral.chainofresponsibility.LoanApplication;

import java.math.BigDecimal;

/** CONCRETE HANDLER: approves anything up to its authority limit. */
public class LimitApprover extends LoanApprover {

    private final String role;
    private final BigDecimal limit;

    public LimitApprover(String role, String limit) {
        this.role = role;
        this.limit = new BigDecimal(limit);
    }

    @Override
    protected boolean canHandle(LoanApplication app) {
        return app.amount().compareTo(limit) <= 0;
    }

    @Override
    protected Decision decide(LoanApplication app) {
        return new Decision(role, "APPROVED");
    }
}
