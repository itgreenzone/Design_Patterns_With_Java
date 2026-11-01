# Lesson 22 – Observer Pattern

**ITGreenZone – GoF Design Patterns in Java** · [itgreenzone.com](https://itgreenzone.com)

> **Intent:** define a one-to-many dependency so that when one object changes state, all its dependents are notified automatically.

## Scenario

A stock-price alert system. When a stock's price changes, subscribed **investors**, a **portfolio revaluation** service and a **risk-limit monitor** must all be notified.

## Packages

| Package    | What it shows |
|------------|---------------|
| `before`   | `StockTicker` calls every interested service directly. Each new listener means editing the ticker. |
| `after`    | **Subject** `StockTicker` (subscribe / unsubscribe / notify), **Observer** `PriceListener`, **Concrete Observers** `InvestorAlert`, `PortfolioRevaluer`, `RiskLimitMonitor`, and a pull-model `PullDashboard`. |
| `modern`   | `Consumer<PriceUpdate>` lambdas with a closeable `Subscription`, plus `java.util.concurrent.Flow` via `SubmissionPublisher`. |
| `exercise` | Add a `DailyHighLowObserver` and test unsubscribe. |
| `solution` | Reference solution + tests. |

We do **not** use `java.util.Observable` / `Observer`. They were deprecated in Java 9: `Observable` is a class rather than an interface, so you must extend it; its event model is untyped (`Object arg`); it isn't thread-safe enough for real use; and notification order is unspecified.

Money uses `BigDecimal` at 2 dp, `HALF_UP`.

## How to run

**IntelliJ IDEA:** right-click `after.AfterDemo` and choose **Run**. To run the tests, right-click `src/test/java` and choose **Run 'All Tests'**.

```bash
mvn test
mvn compile exec:java -Dexec.mainClass=com.itgreenzone.patterns.behavioral.observer.after.AfterDemo
```

## Exercise (≈20 min)

Implement `exercise.DailyHighLowObserver`, then write a test that unsubscribes it. Compare with `solution`.
