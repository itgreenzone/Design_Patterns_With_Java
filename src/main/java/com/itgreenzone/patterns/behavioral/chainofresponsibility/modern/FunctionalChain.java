package com.itgreenzone.patterns.behavioral.chainofresponsibility.modern;

import com.itgreenzone.patterns.behavioral.chainofresponsibility.Decision;
import com.itgreenzone.patterns.behavioral.chainofresponsibility.LoanApplication;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * MODERN VARIANT: the chain as a LIST of functions. Each returns Optional.empty()
 * to say "not me"; the first non-empty answer wins. The order is just the list order.
 */
public final class FunctionalChain {

    private FunctionalChain() {
    }

    public static Function<LoanApplication, Optional<Decision>> limit(String role, String max) {
        BigDecimal m = new BigDecimal(max);
        return app -> app.amount().compareTo(m) <= 0 ? Optional.of(new Decision(role, "APPROVED")) : Optional.empty();
    }

    public static Decision route(List<Function<LoanApplication, Optional<Decision>>> handlers, LoanApplication app) {
        return handlers.stream()
                .map(h -> h.apply(app))
                .flatMap(Optional::stream)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Unhandled"));
    }
}
