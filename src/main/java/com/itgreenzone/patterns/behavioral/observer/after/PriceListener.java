package com.itgreenzone.patterns.behavioral.observer.after;

import com.itgreenzone.patterns.behavioral.observer.PriceUpdate;

/** OBSERVER: anything that wants to hear about price changes (push model). */
@FunctionalInterface
public interface PriceListener {

    void onPrice(PriceUpdate update);
}
