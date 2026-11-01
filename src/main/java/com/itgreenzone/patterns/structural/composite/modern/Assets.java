package com.itgreenzone.patterns.structural.composite.modern;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * MODERN VARIANT: a SEALED tree of records + pattern-matching switch.
 * This fits here because the SHAPE of the tree is fixed (a holding or a fund),
 * so the compiler can check every switch is exhaustive. Operations live in
 * functions instead of in the classes.
 */
public final class Assets {

    private Assets() {
    }

    public sealed interface Asset permits Holding, Fund {
    }

    public record Holding(String name, BigDecimal value, BigDecimal feeRate) implements Asset {
    }

    public record Fund(String name, List<Asset> children) implements Asset {
        public Fund {
            children = List.copyOf(children);
        }
    }

    public static BigDecimal value(Asset a) {
        return switch (a) {
            case Holding h -> h.value();
            case Fund f -> f.children().stream().map(Assets::value).reduce(BigDecimal.ZERO, BigDecimal::add);
        };
    }

    public static BigDecimal fees(Asset a) {
        return switch (a) {
            case Holding h -> h.value().multiply(h.feeRate()).setScale(2, RoundingMode.HALF_UP);
            case Fund f -> f.children().stream().map(Assets::fees).reduce(BigDecimal.ZERO.setScale(2), BigDecimal::add);
        };
    }

    public static int countHoldings(Asset a) {
        return switch (a) {
            case Holding h -> 1;
            case Fund f -> f.children().stream().mapToInt(Assets::countHoldings).sum();
        };
    }
}
