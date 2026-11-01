package com.itgreenzone.patterns.structural.proxy.after;

import com.itgreenzone.patterns.structural.proxy.CreditReport;
import com.itgreenzone.patterns.structural.proxy.User;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * REAL SUBJECT: calls the external bureau. Expensive to CREATE (opens a secure
 * session) and expensive to CALL (slow, and SGD 2.50 per report).
 */
public class BureauCreditReportService implements CreditReportService {

    public static final AtomicInteger INSTANCES = new AtomicInteger();
    private final AtomicInteger calls = new AtomicInteger();

    public BureauCreditReportService() {
        INSTANCES.incrementAndGet();             // pretend: TLS handshake, login, session
    }

    @Override
    public CreditReport reportFor(String customerId, User requester) {
        calls.incrementAndGet();
        int score = 500 + Math.abs(customerId.hashCode() % 350);
        return new CreditReport(customerId, score, "Full bureau report for " + customerId);
    }

    public int calls() {
        return calls.get();
    }

    public BigDecimal costSoFar() {
        return new BigDecimal("2.50").multiply(BigDecimal.valueOf(calls.get()));
    }
}
