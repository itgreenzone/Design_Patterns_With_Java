package com.itgreenzone.patterns.creational.singleton.solution;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class AuditLoggerTest {

    @BeforeEach
    void reset() {
        AuditLogger.getInstance().clearForTests();
    }

    @Test
    void hundredThreadsShareOneLoggerAndLoseNoEntries() throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(100);
        CountDownLatch start = new CountDownLatch(1);
        List<Future<AuditLogger>> results = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            int n = i;
            results.add(pool.submit(() -> {
                start.await();
                AuditLogger logger = AuditLogger.getInstance();
                logger.log("event-" + n);
                return logger;
            }));
        }
        start.countDown();
        Set<AuditLogger> distinct = Collections.newSetFromMap(new IdentityHashMap<>());
        for (Future<AuditLogger> f : results) distinct.add(f.get(10, TimeUnit.SECONDS));
        pool.shutdown();

        assertEquals(1, distinct.size());
        assertEquals(100, AuditLogger.getInstance().entries().size());
    }

    @Test
    void entriesAreReadOnly() {
        AuditLogger.getInstance().log("login");
        assertThrows(UnsupportedOperationException.class, () -> AuditLogger.getInstance().entries().clear());
    }
}
