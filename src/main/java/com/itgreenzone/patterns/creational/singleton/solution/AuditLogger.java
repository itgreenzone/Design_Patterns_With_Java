package com.itgreenzone.patterns.creational.singleton.solution;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/** Reference solution: holder idiom + a thread-safe list. */
public final class AuditLogger {

    private final List<String> entries = new CopyOnWriteArrayList<>();

    private AuditLogger() {
    }

    private static final class Holder {
        private static final AuditLogger INSTANCE = new AuditLogger();
    }

    public static AuditLogger getInstance() {
        return Holder.INSTANCE;
    }

    public void log(String event) {
        entries.add(event);
    }

    public List<String> entries() {
        return List.copyOf(entries);
    }

    /** Test helper only: singletons keep state between tests, which is one of their downsides. */
    void clearForTests() {
        entries.clear();
    }
}
