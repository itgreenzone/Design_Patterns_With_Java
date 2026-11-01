package com.itgreenzone.patterns.structural.proxy.after;

import com.itgreenzone.patterns.structural.proxy.User;

import java.time.Clock;
import java.time.Duration;
import java.util.Set;

/** Protection → caching → lazy → real bureau. Callers see one CreditReportService. */
public class AfterDemo {

    public static void main(String[] args) {
        BureauCreditReportService[] real = new BureauCreditReportService[1];
        CreditReportService service = new SecuredCreditReportService(
                new CachingCreditReportService(
                        new LazyCreditReportService(() -> real[0] = new BureauCreditReportService()),
                        Duration.ofHours(24), Clock.systemUTC()));

        User underwriter = new User("Mei", Set.of("UNDERWRITER"));
        service.reportFor("S1234567A", underwriter);
        service.reportFor("S1234567A", underwriter);
        System.out.println("Bureau calls: " + real[0].calls() + ", cost SGD " + real[0].costSoFar());
        try {
            service.reportFor("S1234567A", new User("Tom", Set.of("TELLER")));
        } catch (RuntimeException e) {
            System.out.println("Blocked: " + e.getMessage());
        }
    }
}
