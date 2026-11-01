package com.itgreenzone.patterns.structural.adapter.after;

import com.itgreenzone.patterns.structural.adapter.CreditCheckUnavailableException;
import com.itgreenzone.patterns.structural.adapter.CreditScore;

/** CLIENT: pure business rules. It knows nothing about XML, scales or vendors. */
public class LoanService {

    private final CreditScoreProvider scores;

    public LoanService(CreditScoreProvider scores) {
        this.scores = scores;
    }

    public String decide(String customerId) {
        try {
            CreditScore score = scores.scoreFor(customerId);
            if (score.value() >= 650) return "APPROVE";
            if (score.value() >= 580) return "REFER";
            return "DECLINE";
        } catch (CreditCheckUnavailableException e) {
            return "REFER";
        }
    }
}
