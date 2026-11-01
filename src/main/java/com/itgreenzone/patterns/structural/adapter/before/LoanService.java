package com.itgreenzone.patterns.structural.adapter.before;

import com.itgreenzone.patterns.structural.adapter.vendor.BureauException;
import com.itgreenzone.patterns.structural.adapter.vendor.LegacyBureauClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * BEFORE: business code talks to the vendor SDK directly.
 * XML parsing, scale conversion and the checked exception leak into the loan rules,
 * and switching vendor means rewriting this class (and every other caller).
 */
public class LoanService {

    private static final Pattern RATING = Pattern.compile("<rating>(\\d+)</rating>");
    private final LegacyBureauClient bureau;

    public LoanService(LegacyBureauClient bureau) {
        this.bureau = bureau;
    }

    public String decide(String customerId) {
        try {
            String xml = bureau.fetchReportXml(customerId);
            Matcher m = RATING.matcher(xml);
            if (!m.find()) {
                return "REFER";
            }
            int score = 300 + Integer.parseInt(m.group(1)) * 550 / 1000;   // 0–1000 -> 300–850
            if (score >= 650) return "APPROVE";
            if (score >= 580) return "REFER";
            return "DECLINE";
        } catch (BureauException e) {
            return "REFER";
        }
    }
}
