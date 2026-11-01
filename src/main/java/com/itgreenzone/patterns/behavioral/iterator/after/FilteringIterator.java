package com.itgreenzone.patterns.behavioral.iterator.after;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/** An iterator that wraps another and yields only matching elements (e.g. large debits). */
public class FilteringIterator<T> implements Iterator<T> {

    private final Iterator<T> source;
    private final Predicate<T> keep;
    private T lookahead;
    private boolean ready;

    public FilteringIterator(Iterator<T> source, Predicate<T> keep) {
        this.source = source;
        this.keep = keep;
    }

    @Override
    public boolean hasNext() {
        while (!ready && source.hasNext()) {
            T candidate = source.next();
            if (keep.test(candidate)) {
                lookahead = candidate;
                ready = true;
            }
        }
        return ready;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        ready = false;
        return lookahead;
    }
}
