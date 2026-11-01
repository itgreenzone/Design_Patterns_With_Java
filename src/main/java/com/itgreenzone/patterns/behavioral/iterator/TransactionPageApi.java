package com.itgreenzone.patterns.behavioral.iterator;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Simulates the core-banking API: transactions come back ONE MONTH (page) at a time.
 * It counts page fetches so tests can prove the iterator is lazy.
 */
public class TransactionPageApi {

    private final Map<YearMonth, List<Transaction>> pages = new TreeMap<>();
    private final AtomicInteger fetches = new AtomicInteger();

    public void put(YearMonth month, List<Transaction> txns) {
        pages.put(month, List.copyOf(txns));
    }

    public List<Transaction> fetchPage(YearMonth month) {
        fetches.incrementAndGet();
        return pages.getOrDefault(month, List.of());
    }

    public int fetches() {
        return fetches.get();
    }
}
