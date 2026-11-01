package com.itgreenzone.patterns.behavioral.chainofresponsibility.before;

import com.itgreenzone.patterns.behavioral.chainofresponsibility.Decision;
import com.itgreenzone.patterns.behavioral.chainofresponsibility.LoanApplication;

import java.math.BigDecimal;

/**
 * BEFORE: one if/else ladder knows every approval level and limit.
 * Adding a Risk Officer for high-risk applicants, or changing a limit,
 * means editing (and re-testing) the whole ladder.
 */
public class LoanApprovalService {

    public Decision route(LoanApplication app) {
        BigDecimal amount = app.amount();
        if (amount.compareTo(new BigDecimal("50000")) <= 0) {
            return new Decision("Loan Officer", "APPROVED");
        } else if (amount.compareTo(new BigDecimal("250000")) <= 0) {
            return new Decision("Branch Manager", "APPROVED");
        } else if (amount.compareTo(new BigDecimal("1000000")) <= 0) {
            return new Decision("Regional Director", "APPROVED");
        } else {
            return new Decision("Credit Committee", "APPROVED");
        }
    }
}
