package com.itgreenzone.patterns.behavioral.iterator.after;

import com.itgreenzone.patterns.behavioral.iterator.Transaction;
import com.itgreenzone.patterns.behavioral.iterator.TransactionPageApi;

import java.time.YearMonth;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * CONCRETE AGGREGATE: implements Iterable, so callers can use for-each.
 * Each call to iterator() starts a NEW, independent traversal.
 */
public class TransactionHistory implements Iterable<Transaction> {

    private final TransactionPageApi api;
    private final YearMonth from;
    private final YearMonth to;

    public TransactionHistory(TransactionPageApi api, YearMonth from, YearMonth to) {
        this.api = api;
        this.from = from;
        this.to = to;
    }

    @Override
    public Iterator<Transaction> iterator() {
        return new PagedTransactionIterator(api, from, to);
    }

    /** MODERN: expose a lazy Stream built on the same iterator. */
    public Stream<Transaction> stream() {
        return StreamSupport.stream(spliterator(), false);
    }
}
