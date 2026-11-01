package com.itgreenzone.patterns.structural.proxy.before;

import com.itgreenzone.patterns.structural.proxy.AccessDeniedException;
import com.itgreenzone.patterns.structural.proxy.CreditReport;
import com.itgreenzone.patterns.structural.proxy.User;
import com.itgreenzone.patterns.structural.proxy.after.BureauCreditReportService;

import java.util.HashMap;
import java.util.Map;

/**
 * BEFORE: each caller does its own lazy creation, role check and caching.
 * This screen does all three (and its HashMap cache never expires);
 * the card-limit screen next door does none of them.
 */
public class UnderwritingScreen {

    private BureauCreditReportService bureau;                 // lazy, by hand
    private final Map<String, CreditReport> cache = new HashMap<>();

    public CreditReport show(String customerId, User user) {
        if (!user.hasRole("UNDERWRITER")) {                    // security, by hand
            throw new AccessDeniedException("not allowed");
        }
        if (cache.containsKey(customerId)) {                   // cache, by hand (no expiry!)
            return cache.get(customerId);
        }
        if (bureau == null) {
            bureau = new BureauCreditReportService();
        }
        CreditReport r = bureau.reportFor(customerId, user);
        cache.put(customerId, r);
        return r;
    }
}
