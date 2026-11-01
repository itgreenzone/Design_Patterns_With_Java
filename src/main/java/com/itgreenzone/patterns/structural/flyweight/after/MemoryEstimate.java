package com.itgreenzone.patterns.structural.flyweight.after;

/**
 * Rough, teaching-level estimate (not a JVM measurement): ~2,300 bytes of heavy
 * cabin data vs ~32 bytes of per-seat data.
 */
public final class MemoryEstimate {

    public static final long HEAVY_BYTES = 2_300;
    public static final long LIGHT_BYTES = 32;

    private MemoryEstimate() {
    }

    public static long withoutFlyweight(long seats) {
        return seats * (HEAVY_BYTES + LIGHT_BYTES);
    }

    public static long withFlyweight(long seats, long sharedProducts) {
        return sharedProducts * HEAVY_BYTES + seats * LIGHT_BYTES;
    }
}
