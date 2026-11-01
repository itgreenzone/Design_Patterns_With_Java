# Lesson 08 – Prototype Pattern

**ITGreenZone – GoF Design Patterns in Java** · [itgreenzone.com](https://itgreenzone.com)

> **Intent:** create new objects by copying a prototypical instance, instead of building them from scratch.

## Scenario

Insurance agents create customer quotes from pre-configured templates such as **"Family Travel Gold"**. Building a template means calling the rules engine and the database, which is expensive. Agents copy a template and tweak a few fields.

## Packages

| Package    | What it shows |
|------------|---------------|
| `before`   | Two bad options: rebuild the template for every quote (slow), or share one template object (fast, but one agent's change leaks into another customer's quote). |
| `after`    | **Prototype** interface, **Concrete Prototype** `TravelQuote` with a deep-copy `copy()`, and the **Registry** `QuoteTemplateRegistry` that loads each template once and hands out copies. `shallowCopy()` shows the classic bug. |
| `modern`   | Immutable **records** with "wither" methods: copying is trivial and safe. |
| `exercise` | Deep-copy a `FlightItinerary` with nested segments. |
| `solution` | Reference solution + tests. |

**Why not `Cloneable`/`clone()`?** It's a broken design (Effective Java, Item 13): `clone()` is protected on `Object`, it bypasses constructors, it returns `Object`, it is shallow by default, and it doesn't work with `final` fields. Prefer a copy constructor or a `copy()` method.

## How to run

**IntelliJ IDEA:** right-click `after.AfterDemo` and choose **Run**. To run the tests, right-click `src/test/java` and choose **Run 'All Tests'**.

```bash
mvn test
mvn compile exec:java -Dexec.mainClass=com.itgreenzone.patterns.creational.prototype.after.AfterDemo
```

## Exercise (≈20 min)

Implement `exercise.FlightItinerary.deepCopy(...)`, and write a test that would fail with a shallow copy. Compare with `solution`.
