package com.itgreenzone.patterns.foundations.intro.structural;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CoreBankingAdapterTest {

    private final AccountBalanceService service =
            new CoreBankingAdapter(new LegacyCoreBankingSystem(Map.of("SA-1", 123_456L)));

    @Test
    void convertsCentsToBigDecimal() {
        assertEquals(Optional.of(new BigDecimal("1234.56")), service.balanceOf("SA-1"));
    }

    @Test
    void unknownAccountBecomesEmptyInsteadOfMinusOne() {
        assertTrue(service.balanceOf("NOPE").isEmpty());
    }
}
