package com.itgreenzone.patterns.behavioral.observer.modern;

/** A handle that unsubscribes when closed; works with try-with-resources. */
@FunctionalInterface
public interface Subscription extends AutoCloseable {

    @Override
    void close();
}
