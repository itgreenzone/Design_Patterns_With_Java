package com.itgreenzone.patterns.foundations.intro.behavioral;

import java.math.BigDecimal;

/** An immutable posted transaction. */
public record Transaction(String accountNumber, BigDecimal amount, String description) {
}
