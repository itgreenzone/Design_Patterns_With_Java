package com.itgreenzone.patterns.creational.abstractfactory.modern;

import com.itgreenzone.patterns.creational.abstractfactory.after.*;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * MODERN VARIANT: the whole family as ONE immutable record, built in one place.
 * A registry of Suppliers picks the kit by region code (from config, a header, etc.).
 * New regions are registered, not hard-coded, so this stays open for extension.
 */
public record RegionalKit(PaymentGateway gateway, TaxCalculator tax, ReceiptFormatter receipt) {

    public RegionalKit {
        Objects.requireNonNull(gateway);
        Objects.requireNonNull(tax);
        Objects.requireNonNull(receipt);
    }

    public static RegionalKit from(RegionalPaymentFactory f) {
        return new RegionalKit(f.createGateway(), f.createTaxCalculator(), f.createReceiptFormatter());
    }

    private static final Map<String, Supplier<RegionalKit>> REGISTRY = new ConcurrentHashMap<>(Map.of(
            "SG", () -> from(new SingaporePaymentFactory()),
            "MY", () -> from(new MalaysiaPaymentFactory())));

    public static void register(String region, Supplier<RegionalKit> supplier) {
        REGISTRY.put(region, supplier);
    }

    public static RegionalKit forRegion(String region) {
        Supplier<RegionalKit> s = REGISTRY.get(region.toUpperCase());
        if (s == null) {
            throw new IllegalArgumentException("Unsupported region " + region);
        }
        return s.get();
    }
}
