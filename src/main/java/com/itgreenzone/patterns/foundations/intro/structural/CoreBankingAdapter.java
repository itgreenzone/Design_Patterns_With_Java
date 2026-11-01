package com.itgreenzone.patterns.foundations.intro.structural;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * STRUCTURAL preview: wrap the legacy system so it fits our interface.
 *
 * The odd conventions (cents, -1) are translated in ONE class instead of
 * leaking into every screen. (Full pattern: Lesson 09 – Adapter.)
 */
public class CoreBankingAdapter implements AccountBalanceService {

    private final LegacyCoreBankingSystem legacy;

    public CoreBankingAdapter(LegacyCoreBankingSystem legacy) {
        this.legacy = legacy;
    }

    @Override
    public Optional<BigDecimal> balanceOf(String accountNumber) {
        long cents = legacy.getBal(accountNumber);
        if (cents == -1L) {
            return Optional.empty();
        }
        return Optional.of(BigDecimal.valueOf(cents, 2)); // 123456 cents -> 1234.56
    }
}
