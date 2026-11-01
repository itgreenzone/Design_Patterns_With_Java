package com.itgreenzone.patterns.behavioral.observer.modern;

import com.itgreenzone.patterns.behavioral.observer.PriceUpdate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * MODERN VARIANT: observers are plain Consumer<PriceUpdate> lambdas, and
 * subscribe() returns a Subscription so forgetting to unsubscribe is harder.
 */
public class LambdaTicker {

    private final Map<String, BigDecimal> prices = new ConcurrentHashMap<>();
    private final List<Consumer<PriceUpdate>> listeners = new CopyOnWriteArrayList<>();

    public Subscription subscribe(Consumer<PriceUpdate> listener) {
        listeners.add(listener);
        return () -> listeners.remove(listener);
    }

    public void setPrice(String symbol, BigDecimal price) {
        BigDecimal old = prices.getOrDefault(symbol, BigDecimal.ZERO);
        PriceUpdate update = new PriceUpdate(symbol, old, price);
        prices.put(symbol, update.newPrice());
        listeners.forEach(l -> l.accept(update));
    }

    public int listenerCount() {
        return listeners.size();
    }
}
