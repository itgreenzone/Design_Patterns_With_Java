package com.itgreenzone.patterns.creational.singleton.exercise;

import java.util.List;

/**
 * HANDS-ON EXERCISE (≈20 minutes)
 *
 * Every banking service must write to ONE shared audit trail.
 * Make AuditLogger a THREAD-SAFE, LAZY singleton:
 *   1. a private constructor;
 *   2. getInstance() using the holder idiom (or an enum, if you prefer);
 *   3. log(String) must be safe when 100 threads log at once (hint: a thread-safe list);
 *   4. entries() returns an unmodifiable copy.
 * Then write a test that starts 100 threads, logs once from each, and checks
 * that every thread got the SAME instance and that there are exactly 100 entries.
 * Reference solution: ..singleton.solution.AuditLogger and its test.
 */
public final class AuditLogger {

    public static AuditLogger getInstance() {
        throw new UnsupportedOperationException("TODO");
    }

    public void log(String event) {
        throw new UnsupportedOperationException("TODO");
    }

    public List<String> entries() {
        throw new UnsupportedOperationException("TODO");
    }
}
