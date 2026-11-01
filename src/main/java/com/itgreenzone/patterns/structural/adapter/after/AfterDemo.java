package com.itgreenzone.patterns.structural.adapter.after;

import com.itgreenzone.patterns.structural.adapter.vendor.LegacyBureauClient;
import com.itgreenzone.patterns.structural.adapter.vendor.ModernScoreApi;

import java.util.Map;

/** The same LoanService works with two very different vendors, through adapters. */
public class AfterDemo {

    public static void main(String[] args) {
        LoanService viaLegacy = new LoanService(new BureauCreditScoreAdapter(new LegacyBureauClient(Map.of("S1234567A", 800))));
        LoanService viaModern = new LoanService(new ModernBureauAdapter(new ModernScoreApi(Map.of("S1234567A", 780))));
        System.out.println("Legacy vendor: " + viaLegacy.decide("S1234567A"));
        System.out.println("Modern vendor: " + viaModern.decide("S1234567A"));
        System.out.println("Unknown customer: " + viaLegacy.decide("S0000000Z"));
    }
}
