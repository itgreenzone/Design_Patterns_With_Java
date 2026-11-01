package com.itgreenzone.patterns.behavioral.observer.modern;

import com.itgreenzone.patterns.behavioral.observer.PriceUpdate;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.SubmissionPublisher;

/**
 * MODERN VARIANT: java.util.concurrent.Flow (reactive streams, Java 9+).
 * SubmissionPublisher delivers asynchronously on another thread, with
 * back-pressure, so a slow subscriber doesn't block the publisher.
 */
public final class FlowPriceFeed {

    private FlowPriceFeed() {
    }

    /** Publishes the updates and returns what an asynchronous subscriber received. */
    public static List<PriceUpdate> publishAndCollect(List<PriceUpdate> updates) throws Exception {
        List<PriceUpdate> received = new CopyOnWriteArrayList<>();
        SubmissionPublisher<PriceUpdate> publisher = new SubmissionPublisher<>();
        CompletableFuture<Void> done = publisher.consume(received::add);
        updates.forEach(publisher::submit);
        publisher.close();                 // completes the subscriber after all items
        done.get();
        return List.copyOf(received);
    }
}
