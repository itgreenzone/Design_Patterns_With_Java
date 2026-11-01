# Lesson 14 – Flyweight Pattern

**ITGreenZone – GoF Design Patterns in Java** · [itgreenzone.com](https://itgreenzone.com)

> **Intent:** use sharing to support large numbers of fine-grained objects efficiently.

## Scenario

An airline seat-map service renders seat maps for hundreds of flights a day. An A380 here has **459 seats**, but only **4 cabin products** (Suites, Business, Premium Economy, Economy). Each product carries heavy data (pitch, recline, amenities and a 2 KB icon); each seat only needs its row, letter and occupied flag.

## Packages

| Package    | What it shows |
|------------|---------------|
| `before`   | Every `Seat` carries all the heavy data: 459 copies per aircraft. |
| `after`    | **Flyweight** `CabinProduct` (immutable intrinsic state), **Flyweight Factory** `CabinProductFactory` (`computeIfAbsent` cache), and a tiny `Seat` holding the extrinsic state plus a shared reference. `MemoryEstimate` compares the two designs for 300 flights. |
| `modern`   | `SeatProduct` record with a static interning cache, like `Integer.valueOf()`. |
| `exercise` | A thread-safe `InstrumentRegistry` sharing `InstrumentInfo` across millions of `MarketTick`s. |
| `solution` | Reference solution + tests. |

**Only use it after measuring:** Flyweight trades simplicity for memory. Use a profiler (or JFR) to confirm a real memory problem first.

## How to run

**IntelliJ IDEA:** right-click `after.AfterDemo` and choose **Run**. To run the tests, right-click `src/test/java` and choose **Run 'All Tests'**.

```bash
mvn test
mvn compile exec:java -Dexec.mainClass=com.itgreenzone.patterns.structural.flyweight.after.AfterDemo
```

## Exercise (≈20 min)

Implement `exercise.InstrumentRegistry`. Compare with `solution`.
