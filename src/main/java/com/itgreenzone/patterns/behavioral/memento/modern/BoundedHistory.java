package com.itgreenzone.patterns.behavioral.memento.modern;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

/**
 * MODERN VARIANT: a generic caretaker with a capacity limit, because mementos
 * cost memory. When full, the oldest snapshot is dropped.
 */
public class BoundedHistory<T> {

    private final Deque<T> stack = new ArrayDeque<>();
    private final int capacity;

    public BoundedHistory(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("capacity must be at least 1");
        }
        this.capacity = capacity;
    }

    public void push(T snapshot) {
        if (stack.size() == capacity) {
            stack.removeLast();
        }
        stack.push(snapshot);
    }

    public Optional<T> pop() {
        return Optional.ofNullable(stack.poll());
    }

    public int size() {
        return stack.size();
    }
}
