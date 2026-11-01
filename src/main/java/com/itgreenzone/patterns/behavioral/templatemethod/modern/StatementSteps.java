package com.itgreenzone.patterns.behavioral.templatemethod.modern;

import com.itgreenzone.patterns.behavioral.templatemethod.Account;
import com.itgreenzone.patterns.behavioral.templatemethod.Figures;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

/**
 * MODERN VARIANT: the variable steps as functions (composition instead of inheritance).
 * fee is optional (a "hook" with a default).
 */
public record StatementSteps(BiFunction<Account, BigDecimal, BigDecimal> interest,
                             UnaryOperator<BigDecimal> fee,
                             BiFunction<Account, Figures, String> format) {

    public StatementSteps {
        Objects.requireNonNull(interest);
        Objects.requireNonNull(format);
        fee = fee == null ? b -> BigDecimal.ZERO : fee;
    }
}
