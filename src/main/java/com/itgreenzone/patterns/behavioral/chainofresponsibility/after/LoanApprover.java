package com.itgreenzone.patterns.behavioral.chainofresponsibility.after;

import com.itgreenzone.patterns.behavioral.chainofresponsibility.Decision;
import com.itgreenzone.patterns.behavioral.chainofresponsibility.LoanApplication;

/**
 * HANDLER: either handles the request or passes it to the next handler.
 * Each handler knows only its OWN rule and its successor – not the whole chain.
 */
public abstract class LoanApprover {

    private LoanApprover next;

    /** Returns the next handler so chains read fluently: a.setNext(b).setNext(c). */
    public LoanApprover setNext(LoanApprover next) {
        this.next = next;
        return next;
    }

    public final Decision approve(LoanApplication app) {
        if (canHandle(app)) {
            return decide(app);
        }
        if (next == null) {
            throw new IllegalStateException("Nobody in the chain can handle " + app.amount());
        }
        return next.approve(app);
    }

    protected abstract boolean canHandle(LoanApplication app);

    protected abstract Decision decide(LoanApplication app);
}
