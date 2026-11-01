package com.itgreenzone.patterns.behavioral.chainofresponsibility;

import com.itgreenzone.patterns.behavioral.chainofresponsibility.after.*;
import com.itgreenzone.patterns.behavioral.chainofresponsibility.after.validation.ValidationPipeline;
import com.itgreenzone.patterns.behavioral.chainofresponsibility.before.LoanApprovalService;
import com.itgreenzone.patterns.behavioral.chainofresponsibility.modern.FunctionalChain;
import com.itgreenzone.patterns.behavioral.chainofresponsibility.solution.FraudEscalationHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ChainTest {

    @ParameterizedTest
    @CsvSource({"50000, Loan Officer", "50000.01, Branch Manager", "250000, Branch Manager",
                "1000000, Regional Director", "1000001, Credit Committee"})
    void eachAmountReachesTheRightLevel(String amount, String expected) {
        LoanApplication app = LoanApplication.of("C1", amount);
        assertEquals(expected, ApprovalChains.standard().approve(app).handledBy());
        assertEquals(expected, new LoanApprovalService().route(app).handledBy(), "same routing as before");
    }

    @Test
    void riskOfficerInsertedWithoutChangingOthers() {
        LoanApprover chain = ApprovalChains.withRiskReview();
        assertEquals("Risk Officer", chain.approve(LoanApplication.of("C1", "180000").asHighRisk()).handledBy());
        assertEquals("Branch Manager", chain.approve(LoanApplication.of("C2", "180000")).handledBy());
        assertEquals("Regional Director", chain.approve(LoanApplication.of("C3", "600000").asHighRisk()).handledBy(),
                "high-risk but above the risk officer's limit passes on");
    }

    @Test
    void chainWithoutAFinalHandlerFailsLoudly() {
        LoanApprover onlyOfficer = new LimitApprover("Loan Officer", "50000");
        assertThrows(IllegalStateException.class, () -> onlyOfficer.approve(LoanApplication.of("C1", "60000")));
    }

    @Test
    void pipelineRunsEveryCheckAndCollectsAllProblems() {
        ValidationPipeline pipeline = new ValidationPipeline(List.of(
                ValidationPipeline.kyc(), ValidationPipeline.blacklist(Set.of("BAD1")), ValidationPipeline.income()));
        LoanApplication bad = new LoanApplication("BAD1", new BigDecimal("1200000"), new BigDecimal("5000"), false, false, false);
        assertEquals(List.of("KYC documents missing", "Applicant is blacklisted", "Instalment exceeds 40% of income"),
                pipeline.validate(bad));
        assertTrue(pipeline.validate(LoanApplication.of("OK1", "100000")).isEmpty());
    }

    @Test
    void functionalChainFirstAnswerWins() {
        var handlers = List.of(FunctionalChain.limit("Loan Officer", "50000"),
                               FunctionalChain.limit("Branch Manager", "250000"));
        assertEquals("Branch Manager", FunctionalChain.route(handlers, LoanApplication.of("C", "100000")).handledBy());
        assertThrows(IllegalStateException.class, () -> FunctionalChain.route(handlers, LoanApplication.of("C", "900000")));
    }

    @Test
    void fraudEscalationSolutionSkipsTheWholeLadder() {
        CreditCommittee committee = new CreditCommittee();
        LoanApprover chain = new FraudEscalationHandler(committee);
        chain.setNext(ApprovalChains.withRiskReview());
        Decision d = chain.approve(LoanApplication.of("C1", "1000").withFraudFlag());
        assertEquals(new Decision("Credit Committee", "INVESTIGATE"), d);
        assertEquals("Loan Officer", chain.approve(LoanApplication.of("C2", "1000")).handledBy());
    }
}
