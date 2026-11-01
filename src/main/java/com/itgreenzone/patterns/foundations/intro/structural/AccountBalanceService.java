package com.itgreenzone.patterns.foundations.intro.structural;

import java.math.BigDecimal;
import java.util.Optional;

/** The clean interface OUR app wants: money as BigDecimal, "unknown" as empty. */
public interface AccountBalanceService {

    Optional<BigDecimal> balanceOf(String accountNumber);
}
