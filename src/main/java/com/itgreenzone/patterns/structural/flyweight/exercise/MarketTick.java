package com.itgreenzone.patterns.structural.flyweight.exercise;

import java.math.BigDecimal;

/** One price update (extrinsic state) + a reference to the shared InstrumentInfo. Millions per day. */
public record MarketTick(InstrumentInfo instrument, BigDecimal price, long epochMillis) {
}
