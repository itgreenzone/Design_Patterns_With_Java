package com.itgreenzone.patterns.structural.proxy.after;

import com.itgreenzone.patterns.structural.proxy.AccessDeniedException;
import com.itgreenzone.patterns.structural.proxy.CreditReport;
import com.itgreenzone.patterns.structural.proxy.User;

/** PROTECTION PROXY: only UNDERWRITERs may see full reports. The check lives in ONE place. */
public class SecuredCreditReportService implements CreditReportService {

    private final CreditReportService target;

    public SecuredCreditReportService(CreditReportService target) {
        this.target = target;
    }

    @Override
    public CreditReport reportFor(String customerId, User requester) {
        if (requester == null || !requester.hasRole("UNDERWRITER")) {
            throw new AccessDeniedException((requester == null ? "anonymous" : requester.name())
                    + " may not view credit reports");
        }
        return target.reportFor(customerId, requester);
    }
}
