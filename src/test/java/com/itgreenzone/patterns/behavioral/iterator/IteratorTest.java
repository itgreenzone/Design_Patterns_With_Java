package com.itgreenzone.patterns.behavioral.iterator;

import com.itgreenzone.patterns.behavioral.iterator.after.FilteringIterator;
import com.itgreenzone.patterns.behavioral.iterator.after.TransactionHistory;
import com.itgreenzone.patterns.behavioral.iterator.before.StatementPrinter;
import com.itgreenzone.patterns.behavioral.iterator.exercise.Leg;
import com.itgreenzone.patterns.behavioral.iterator.solution.FlightItinerary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class IteratorTest {

    private TransactionPageApi api;
    private TransactionHistory history;

    private static Transaction tx(String id, String amount) {
        return new Transaction(id, LocalDate.of(2026, 1, 1), new BigDecimal(amount), id);
    }

    @BeforeEach
    void setUp() {
        api = new TransactionPageApi();
        api.put(YearMonth.of(2026, 1), List.of(tx("A", "-50.00"), tx("B", "-1500.00")));
        // February is empty
        api.put(YearMonth.of(2026, 3), List.of(tx("C", "5000.00"), tx("D", "-2000.00"), tx("E", "-10.00")));
        history = new TransactionHistory(api, YearMonth.of(2026, 1), YearMonth.of(2026, 3));
    }

    @Test
    void forEachWalksAllPagesIncludingEmptyOnes() {
        List<String> ids = new ArrayList<>();
        for (Transaction t : history) ids.add(t.id());
        assertEquals(List.of("A", "B", "C", "D", "E"), ids);
    }

    @Test
    void pagesAreFetchedLazily() {
        Iterator<Transaction> it = history.iterator();
        it.next();
        it.next();
        assertEquals(1, api.fetches(), "only January fetched so far");
        it.next();
        assertEquals(3, api.fetches(), "February (empty) and March fetched on demand");
    }

    @Test
    void hasNextIsSafeToCallRepeatedlyAndNextThrowsAtTheEnd() {
        Iterator<Transaction> it = history.iterator();
        for (int i = 0; i < 5; i++) {
            assertTrue(it.hasNext());
            assertTrue(it.hasNext());
            it.next();
        }
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);
    }

    @Test
    void filteringIteratorAndBeforeAgree() {
        List<String> ids = new ArrayList<>();
        new FilteringIterator<>(history.iterator(), t -> t.amount().compareTo(new BigDecimal("-1000")) <= 0)
                .forEachRemaining(t -> ids.add(t.id()));
        assertEquals(List.of("B", "D"), ids);
        assertEquals(ids, new StatementPrinter().largeDebits(api, YearMonth.of(2026, 1), YearMonth.of(2026, 3))
                .stream().map(Transaction::id).toList());
    }

    @Test
    void eachIteratorIsIndependent() {
        Iterator<Transaction> a = history.iterator(), b = history.iterator();
        a.next();
        assertEquals("A", b.next().id());
    }

    @Test
    void streamIsLazyToo() {
        Optional<Transaction> firstBigDebit = history.stream().filter(Transaction::isDebit)
                .filter(t -> t.amount().compareTo(new BigDecimal("-1000")) <= 0).findFirst();
        assertEquals("B", firstBigDebit.orElseThrow().id());
        assertEquals(1, api.fetches(), "stopped after the first page");
    }

    @Test
    void arrayListIteratorIsFailFast() {
        List<String> list = new ArrayList<>(List.of("a", "b", "c"));
        assertThrows(ConcurrentModificationException.class, () -> {
            for (String s : list) if (s.equals("a")) list.remove(s);
        });
        List<String> safe = new ArrayList<>(List.of("a", "b", "c"));
        safe.removeIf(s -> s.equals("a"));            // the safe way
        assertEquals(List.of("b", "c"), safe);
    }

    @Test
    void itinerarySolutionSkipsCancelledLegs() {
        FlightItinerary trip = new FlightItinerary(List.of(new Leg("SQ1", "SIN", "LHR", false),
                new Leg("BA9", "LHR", "JFK", true), new Leg("AA2", "LHR", "BOS", false), new Leg("XX0", "BOS", "SFO", true)));
        List<String> flights = new ArrayList<>();
        for (Leg leg : trip) flights.add(leg.flightNo());
        assertEquals(List.of("SQ1", "AA2"), flights);
    }
}
