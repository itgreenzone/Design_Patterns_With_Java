package com.itgreenzone.patterns.behavioral.iterator.solution;

import com.itgreenzone.patterns.behavioral.iterator.exercise.Leg;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/** Reference solution: advance past cancelled legs in hasNext(), which is idempotent. */
public class ActiveLegIterator implements Iterator<Leg> {

    private final List<Leg> legs;
    private int index;

    public ActiveLegIterator(List<Leg> legs) {
        this.legs = legs;
    }

    @Override
    public boolean hasNext() {
        while (index < legs.size() && legs.get(index).cancelled()) {
            index++;
        }
        return index < legs.size();
    }

    @Override
    public Leg next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return legs.get(index++);
    }
}
