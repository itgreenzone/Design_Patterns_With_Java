package com.itgreenzone.patterns.structural.flyweight.modern;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MODERN VARIANT: the flyweight as a RECORD (immutable by design) with a static
 * interning cache, like Integer.valueOf(). Note: the icon is referenced by name,
 * because a byte[] field would break record equality.
 */
public record SeatProduct(String cabin, int pitchInches, List<String> amenities, String iconName) {

    private static final Map<SeatProduct, SeatProduct> CACHE = new ConcurrentHashMap<>();

    public SeatProduct {
        amenities = List.copyOf(amenities);
    }

    /** Returns the canonical shared instance for these values. */
    public static SeatProduct of(String cabin, int pitch, List<String> amenities, String iconName) {
        SeatProduct candidate = new SeatProduct(cabin, pitch, amenities, iconName);
        return CACHE.computeIfAbsent(candidate, k -> k);
    }

    public static int cacheSize() {
        return CACHE.size();
    }
}
