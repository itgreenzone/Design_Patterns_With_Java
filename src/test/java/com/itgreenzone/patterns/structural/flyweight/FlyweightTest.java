package com.itgreenzone.patterns.structural.flyweight;

import com.itgreenzone.patterns.structural.flyweight.after.*;
import com.itgreenzone.patterns.structural.flyweight.modern.SeatProduct;
import com.itgreenzone.patterns.structural.flyweight.solution.InstrumentRegistry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class FlyweightTest {

    @Test
    void identicalProductsAreTheSameInstance() {
        CabinProductFactory f = new CabinProductFactory();
        CabinProduct a = f.get("ECONOMY", 32, 6, List.of("11in screen"));
        CabinProduct b = f.get("ECONOMY", 32, 6, List.of("11in screen"));
        assertSame(a, b);
        assertNotSame(a, f.get("ECONOMY", 34, 6, List.of("11in screen")), "different pitch = different product");
    }

    @Test
    void a380Has459SeatsSharingFourProducts() {
        CabinProductFactory f = new CabinProductFactory();
        List<Seat> seats = new SeatMapBuilder(f).a380();
        assertEquals(459, seats.size());
        assertEquals(4, f.distinctProducts());
        Set<CabinProduct> distinct = Collections.newSetFromMap(new IdentityHashMap<>());
        seats.forEach(s -> distinct.add(s.product()));
        assertEquals(4, distinct.size());
    }

    @Test
    void beforeVersionHas459HeavyCopies() {
        var seats = com.itgreenzone.patterns.structural.flyweight.before.SeatMapBuilder.a380();
        Set<byte[]> icons = Collections.newSetFromMap(new IdentityHashMap<>());
        seats.forEach(s -> icons.add(s.icon()));
        assertEquals(459, icons.size());
    }

    @Test
    void extrinsicStateIsPerSeatAndRendersTheSameAsBefore() {
        List<Seat> after = new SeatMapBuilder(new CabinProductFactory()).a380();
        var before = com.itgreenzone.patterns.structural.flyweight.before.SeatMapBuilder.a380();
        after.get(5).setOccupied(true);
        before.get(5).setOccupied(true);
        for (int i = 0; i < after.size(); i++) {
            assertEquals(before.get(i).render(), after.get(i).render());
        }
        assertNotEquals(after.get(5).render(), after.get(6).render(), "occupied flag is NOT shared");
    }

    @Test
    void sharedStateIsImmutable() {
        CabinProduct p = new CabinProductFactory().get("SUITES", 80, 180, new ArrayList<>(List.of("Bed")));
        assertThrows(UnsupportedOperationException.class, () -> p.amenities().add("Hack"));
    }

    @Test
    void estimateShowsTheSaving() {
        long seats = 459L * 300;
        long before = MemoryEstimate.withoutFlyweight(seats);
        long after = MemoryEstimate.withFlyweight(seats, 4);
        assertTrue(before / after > 50, "more than 50x smaller in this estimate");
    }

    @Test
    void factoryIsSafeUnderConcurrency() throws Exception {
        CabinProductFactory f = new CabinProductFactory();
        ExecutorService pool = Executors.newFixedThreadPool(16);
        List<Future<CabinProduct>> results = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            results.add(pool.submit(() -> f.get("BUSINESS", 60, 180, List.of("Flat bed"))));
        }
        Set<CabinProduct> distinct = Collections.newSetFromMap(new IdentityHashMap<>());
        for (Future<CabinProduct> r : results) distinct.add(r.get());
        pool.shutdown();
        assertEquals(1, distinct.size());
    }

    @Test
    void recordInterningCache() {
        SeatProduct a = SeatProduct.of("ECONOMY", 32, List.of("Screen"), "eco.svg");
        SeatProduct b = SeatProduct.of("ECONOMY", 32, List.of("Screen"), "eco.svg");
        assertSame(a, b);
        assertEquals(new SeatProduct("ECONOMY", 32, List.of("Screen"), "eco.svg"), a, "records compare by value");
    }

    @Test
    void instrumentRegistrySolution() {
        InstrumentRegistry registry = new InstrumentRegistry();
        var t1 = registry.tick("D05", new BigDecimal("35.50"), 1L);
        var t2 = registry.tick("D05", new BigDecimal("35.52"), 2L);
        registry.tick("O39", new BigDecimal("12.10"), 3L);
        assertSame(t1.instrument(), t2.instrument());
        assertEquals(2, registry.loads());
    }
}
