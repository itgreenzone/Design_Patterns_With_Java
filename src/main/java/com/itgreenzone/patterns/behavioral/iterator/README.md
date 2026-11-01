# Lesson 19 – Iterator Pattern

**ITGreenZone – GoF Design Patterns in Java** · [itgreenzone.com](https://itgreenzone.com)

> **Intent:** provide a way to access the elements of a collection one by one without exposing how it's stored.

## Scenario

A bank's `TransactionHistory` is stored as **monthly pages** fetched from the core-banking API (`TransactionPageApi`). Callers want to loop over every transaction without knowing about pages.

## Packages

| Package    | What it shows |
|------------|---------------|
| `before`   | `StatementPrinter` writes its own month loop + index loop, and copies them for the debit report. |
| `after`    | **Iterator** `java.util.Iterator<Transaction>`, **Concrete Iterator** `PagedTransactionIterator` (lazy page loading), **Aggregate** `Iterable<Transaction>`, **Concrete Aggregate** `TransactionHistory` (for-each works), plus a reusable `FilteringIterator`. |
| `modern`   | `TransactionHistory.stream()`: a lazy `Stream` built from the same iterator (via its `Spliterator`). |
| `exercise` | Iterate an itinerary's legs, skipping cancelled ones. |
| `solution` | Reference solution + tests. |

**Fail-fast iterators:** `ArrayList`'s iterator throws `ConcurrentModificationException` if the list is changed during a for-each. `IteratorTest` demonstrates it, and shows the safe alternatives (`Iterator.remove()`, `removeIf`).

## How to run

**IntelliJ IDEA:** right-click `after.AfterDemo` and choose **Run**. To run the tests, right-click `src/test/java` and choose **Run 'All Tests'**.

```bash
mvn test
mvn compile exec:java -Dexec.mainClass=com.itgreenzone.patterns.behavioral.iterator.after.AfterDemo
```

## Exercise (≈20 min)

Implement `exercise.ActiveLegIterator`. Compare with `solution`.
