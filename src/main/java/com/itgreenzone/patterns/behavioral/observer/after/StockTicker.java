package com.itgreenzone.patterns.behavioral.observer.after;

import com.itgreenzone.patterns.behavioral.observer.PriceUpdate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * SUBJECT: keeps the list of observers and notifies them. It knows only the
 * PriceListener interface, never the concrete services.
 * CopyOnWriteArrayList lets observers unsubscribe safely during a notification.
 * One failing observer is isolated so the others are still notified.
 */
public class StockTicker {

    private final Map<String, BigDecimal> prices = new ConcurrentHashMap<>();
    private final List<PriceListener> listeners = new CopyOnWriteArrayList<>();
    private final List<String> failures = new CopyOnWriteArrayList<>();

    public void subscribe(PriceListener listener) {
        listeners.add(listener);
    }

    public void unsubscribe(PriceListener listener) {
        listeners.remove(listener);
    }

    public void setPrice(String symbol, BigDecimal price) {
        BigDecimal newPrice = price.setScale(2, RoundingMode.HALF_UP);
        BigDecimal old = prices.getOrDefault(symbol, BigDecimal.ZERO);
        prices.put(symbol, newPrice);
        notifyListeners(new PriceUpdate(symbol, old, newPrice));
    }

    private void notifyListeners(PriceUpdate update) {
        for (PriceListener l : listeners) {
            try {
                l.onPrice(update);
            } catch (RuntimeException e) {
                failures.add(l.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
    }

    /** For the PULL model: observers ask for the data they need. */
    public BigDecimal price(String symbol) {
        return prices.get(symbol);
    }

    public int listenerCount() {
        return listeners.size();
    }

    public List<String> failures() {
        return new ArrayList<>(failures);
    }
}
