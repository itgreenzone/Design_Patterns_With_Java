package com.itgreenzone.patterns.structural.adapter.after;

import com.itgreenzone.patterns.structural.adapter.CreditCheckUnavailableException;
import com.itgreenzone.patterns.structural.adapter.CreditScore;
import com.itgreenzone.patterns.structural.adapter.vendor.ModernScoreApi;

/** A SECOND adapter for a second vendor. LoanService does not change at all. */
public class ModernBureauAdapter implements CreditScoreProvider {

    private final ModernScoreApi api;

    public ModernBureauAdapter(ModernScoreApi api) {
        this.api = api;
    }

    @Override
    public CreditScore scoreFor(String customerId) {
        ModernScoreApi.ScoreResponse r = api.lookup(customerId);
        if (!r.found()) {
            throw new CreditCheckUnavailableException("No record for " + customerId, null);
        }
        return new CreditScore(300 + (r.value() - 300) * 550 / 600);   // 300–900 -> 300–850
    }
}
