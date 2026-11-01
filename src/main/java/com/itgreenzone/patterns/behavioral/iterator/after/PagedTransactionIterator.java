package com.itgreenzone.patterns.behavioral.iterator.after;

import com.itgreenzone.patterns.behavioral.iterator.Transaction;
import com.itgreenzone.patterns.behavioral.iterator.TransactionPageApi;

import java.time.YearMonth;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * CONCRETE ITERATOR: walks every transaction across monthly pages.
 * LAZY: it fetches the next page only when the current one is used up.
 * Callers never see pages, months or indexes.
 */
public class PagedTransactionIterator implements Iterator<Transaction> {

    private final TransactionPageApi api;
    private final YearMonth last;
    private YearMonth nextMonth;
    private List<Transaction> page = List.of();
    private int index;

    public PagedTransactionIterator(TransactionPageApi api, YearMonth from, YearMonth to) {
        this.api = api;
        this.nextMonth = from;
        this.last = to;
    }

    @Override
    public boolean hasNext() {
        while (index >= page.size()) {                 // current page used up
            if (nextMonth.isAfter(last)) {
                return false;
            }
            page = api.fetchPage(nextMonth);           // load the next page only now
            nextMonth = nextMonth.plusMonths(1);
            index = 0;
        }
        return true;
    }

    @Override
    public Transaction next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return page.get(index++);
    }
}
