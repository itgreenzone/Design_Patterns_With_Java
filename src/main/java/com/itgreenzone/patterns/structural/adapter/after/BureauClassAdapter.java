package com.itgreenzone.patterns.structural.adapter.after;

import com.itgreenzone.patterns.structural.adapter.CreditCheckUnavailableException;
import com.itgreenzone.patterns.structural.adapter.CreditScore;
import com.itgreenzone.patterns.structural.adapter.vendor.BureauException;
import com.itgreenzone.patterns.structural.adapter.vendor.LegacyBureauClient;

import java.util.Map;

/**
 * CLASS ADAPTER: EXTENDS the Adaptee and implements the Target.
 * Works, but: Java has single inheritance, it exposes every vendor method
 * (fetchReportXml is still callable), and it can't wrap an existing instance.
 * That's why Java code usually prefers the object adapter.
 */
public class BureauClassAdapter extends LegacyBureauClient implements CreditScoreProvider {

    public BureauClassAdapter(Map<String, Integer> ratings) {
        super(ratings);
    }

    @Override
    public CreditScore scoreFor(String customerId) {
        try {
            String xml = fetchReportXml(customerId);            // inherited
            int rating = Integer.parseInt(xml.replaceAll(".*<rating>(\\d+)</rating>.*", "$1"));
            return new CreditScore(300 + rating * 550 / 1000);
        } catch (BureauException e) {
            throw new CreditCheckUnavailableException("Bureau unavailable", e);
        }
    }
}
