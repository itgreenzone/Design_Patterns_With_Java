package com.itgreenzone.patterns.foundations.intro.structural;

import java.util.Map;

/**
 * A 20-year-old core-banking API we are NOT allowed to change.
 * Odd conventions: balances in cents as long, and -1 means "unknown account".
 */
public class LegacyCoreBankingSystem {

    private final Map<String, Long> balancesInCents;

    public LegacyCoreBankingSystem(Map<String, Long> balancesInCents) {
        this.balancesInCents = Map.copyOf(balancesInCents);
    }

    public long getBal(String acctNo) {
        return balancesInCents.getOrDefault(acctNo, -1L);
    }
}
