package com.itgreenzone.patterns.structural.adapter.after;

import com.itgreenzone.patterns.structural.adapter.CreditCheckUnavailableException;
import com.itgreenzone.patterns.structural.adapter.CreditScore;
import com.itgreenzone.patterns.structural.adapter.vendor.BureauException;
import com.itgreenzone.patterns.structural.adapter.vendor.LegacyBureauClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * OBJECT ADAPTER (recommended): implements the Target and WRAPS the Adaptee.
 * All the translation – method name, XML, scale, exception – lives here, once.
 */
public class BureauCreditScoreAdapter implements CreditScoreProvider {

    private static final Pattern RATING = Pattern.compile("<rating>(\\d+)</rating>");
    private final LegacyBureauClient bureau;          // composition: has-a adaptee

    public BureauCreditScoreAdapter(LegacyBureauClient bureau) {
        this.bureau = bureau;
    }

    @Override
    public CreditScore scoreFor(String customerId) {
        try {
            String xml = bureau.fetchReportXml(customerId);
            Matcher m = RATING.matcher(xml);
            if (!m.find()) {
                throw new CreditCheckUnavailableException("Malformed bureau report", null);
            }
            return new CreditScore(300 + Integer.parseInt(m.group(1)) * 550 / 1000);
        } catch (BureauException e) {
            throw new CreditCheckUnavailableException("Bureau unavailable for " + customerId, e);
        }
    }
}
