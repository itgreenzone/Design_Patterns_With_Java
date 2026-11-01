# Lesson 09 – Adapter Pattern

**ITGreenZone – GoF Design Patterns in Java** · [itgreenzone.com](https://itgreenzone.com)

> **Intent:** convert the interface of a class into another interface that clients expect, so classes with incompatible interfaces can work together.

## Scenario

Our loan system expects a `CreditScoreProvider` (score 300–850). The new credit bureau's legacy SDK (`vendor.LegacyBureauClient`) has a different method name, returns XML, rates on a 0–1000 scale, and throws a **checked** `BureauException`.

## Packages

| Package    | What it shows |
|------------|---------------|
| `vendor`   | The **Adaptees**: `LegacyBureauClient` and a second vendor, `ModernScoreApi` (we can't change either). |
| `before`   | `LoanService` calls the SDK directly: XML parsing, scale maths and the checked exception leak into the business rules. |
| `after`    | **Target** `CreditScoreProvider`, **object adapter** `BureauCreditScoreAdapter` (recommended), **class adapter** `BureauClassAdapter` (for comparison), a second adapter `ModernBureauAdapter`, and the **Client** `LoanService`, which is unchanged when the vendor changes. |
| `modern`   | `ScoreAdapters.fromScale(...)`: a lambda adapter for any id → score function. |
| `exercise` | Adapt a third-party FX feed (inverted quotes, `double`, -1 for unknown) to `ExchangeRateProvider`. |
| `solution` | Reference solution + tests. |

## How to run

**IntelliJ IDEA:** right-click `after.AfterDemo` and choose **Run**. To run the tests, right-click `src/test/java` and choose **Run 'All Tests'**.

```bash
mvn test
mvn compile exec:java -Dexec.mainClass=com.itgreenzone.patterns.structural.adapter.after.AfterDemo
```

## Exercise (≈20 min)

Implement `exercise.FxFeedAdapter`. Compare with `solution`.
