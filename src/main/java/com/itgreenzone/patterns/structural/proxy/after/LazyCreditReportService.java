package com.itgreenzone.patterns.structural.proxy.after;

import com.itgreenzone.patterns.structural.proxy.CreditReport;
import com.itgreenzone.patterns.structural.proxy.User;

import java.util.function.Supplier;

/**
 * VIRTUAL PROXY: delays creating the expensive real service until the first call.
 * Many screens never ask for a report, so they never pay the set-up cost.
 */
public class LazyCreditReportService implements CreditReportService {

    private final Supplier<CreditReportService> factory;
    private volatile CreditReportService real;

    public LazyCreditReportService(Supplier<CreditReportService> factory) {
        this.factory = factory;
    }

    @Override
    public CreditReport reportFor(String customerId, User requester) {
        CreditReportService r = real;
        if (r == null) {
            synchronized (this) {
                if (real == null) {
                    real = factory.get();
                }
                r = real;
            }
        }
        return r.reportFor(customerId, requester);
    }

    public boolean isCreated() {
        return real != null;
    }
}
