package com.itgreenzone.patterns.behavioral.chainofresponsibility.after;

import com.itgreenzone.patterns.behavioral.chainofresponsibility.LoanApplication;

/** Each application travels down the chain until someone can handle it. */
public class AfterDemo {

    public static void main(String[] args) {
        LoanApprover chain = ApprovalChains.withRiskReview();
        System.out.println(chain.approve(LoanApplication.of("C1", "30000")));
        System.out.println(chain.approve(LoanApplication.of("C2", "180000")));
        System.out.println(chain.approve(LoanApplication.of("C3", "180000").asHighRisk()));
        System.out.println(chain.approve(LoanApplication.of("C4", "2500000")));
    }
}
