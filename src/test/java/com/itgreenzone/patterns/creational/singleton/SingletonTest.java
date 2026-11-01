package com.itgreenzone.patterns.creational.singleton;

import com.itgreenzone.patterns.creational.singleton.after.*;
import com.itgreenzone.patterns.creational.singleton.modern.ExchangeRates;
import com.itgreenzone.patterns.creational.singleton.modern.FxConversionService;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class SingletonTest {

    /** Starts all threads at the same moment and collects what getInstance() returned. */
    static <T> Set<T> callConcurrently(int threads, Supplier<T> getInstance) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        CountDownLatch start = new CountDownLatch(1);
        List<Future<T>> results = new ArrayList<>();
        for (int i = 0; i < threads; i++) {
            results.add(pool.submit(() -> { start.await(); return getInstance.get(); }));
        }
        start.countDown();
        Set<T> distinct = Collections.newSetFromMap(new IdentityHashMap<>());
        for (Future<T> f : results) distinct.add(f.get(10, TimeUnit.SECONDS));
        pool.shutdown();
        return distinct;
    }

    @Test
    void beforeVersionLoadsTwiceAndDisagrees() {
        var a = new com.itgreenzone.patterns.creational.singleton.before.ExchangeRateCache();
        var b = new com.itgreenzone.patterns.creational.singleton.before.ExchangeRateCache();
        BigDecimal usd = new BigDecimal("1000.00");
        assertNotEquals(a.toSgd(usd, "USD"), b.toSgd(usd, "USD"));
        assertTrue(RateSource.loads("before") >= 2);
    }

    @Test
    void holderIdiomGivesOneInstanceToFiftyThreads() throws Exception {
        assertEquals(1, callConcurrently(50, ExchangeRateCache::getInstance).size());
        assertEquals(1, RateSource.loads("holder"));
    }

    @Test
    void otherThreadSafeVariantsAlsoGiveOneInstance() throws Exception {
        assertEquals(1, callConcurrently(50, DoubleCheckedExchangeRateCache::getInstance).size());
        assertEquals(1, callConcurrently(50, SynchronizedExchangeRateCache::getInstance).size());
        assertEquals(1, callConcurrently(50, EagerExchangeRateCache::getInstance).size());
        assertEquals(1, callConcurrently(50, () -> ExchangeRates.INSTANCE).size());
    }

    @Test
    void reflectionBreaksAClassicSingleton() throws Exception {
        Constructor<ExchangeRateCache> c = ExchangeRateCache.class.getDeclaredConstructor();
        c.setAccessible(true);
        assertNotSame(ExchangeRateCache.getInstance(), c.newInstance());
    }

    @Test
    void reflectionCannotBreakAnEnumSingleton() {
        Constructor<?> c = ExchangeRates.class.getDeclaredConstructors()[0];
        c.setAccessible(true);
        assertThrows(IllegalArgumentException.class, () -> c.newInstance("COPY", 1));
    }

    @Test
    void readResolveKeepsOneInstanceAfterDeserialisation() throws Exception {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(bytes)) {
            out.writeObject(ExchangeRateCache.getInstance());
        }
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()))) {
            assertSame(ExchangeRateCache.getInstance(), in.readObject());
        }
    }

    @Test
    void injectedProviderMakesServiceTestableWithAFake() {
        FxConversionService service = new FxConversionService((amount, ccy) -> new BigDecimal("100.00"));
        assertEquals(new BigDecimal("102.50"), service.settlementAmount(BigDecimal.ONE, "USD"));
    }

    @Test
    void conversionMaths() {
        BigDecimal sgd = ExchangeRates.INSTANCE.toSgd(new BigDecimal("100.00"), "MYR");
        assertEquals(new BigDecimal("30.00"), sgd);
        assertThrows(IllegalArgumentException.class, () -> ExchangeRates.INSTANCE.toSgd(BigDecimal.ONE, "XYZ"));
    }
}
