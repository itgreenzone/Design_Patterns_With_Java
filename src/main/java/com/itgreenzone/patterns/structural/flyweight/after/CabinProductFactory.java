package com.itgreenzone.patterns.structural.flyweight.after;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * FLYWEIGHT FACTORY: returns the SAME CabinProduct for the same key.
 * computeIfAbsent is atomic, so concurrent seat-map requests can't create duplicates.
 */
public class CabinProductFactory {

    private final ConcurrentMap<String, CabinProduct> cache = new ConcurrentHashMap<>();

    public CabinProduct get(String cabin, int pitch, int recline, List<String> amenities) {
        String key = cabin + "|" + pitch + "|" + recline + "|" + amenities;
        return cache.computeIfAbsent(key, k -> new CabinProduct(cabin, pitch, recline, amenities));
    }

    public int distinctProducts() {
        return cache.size();
    }
}
