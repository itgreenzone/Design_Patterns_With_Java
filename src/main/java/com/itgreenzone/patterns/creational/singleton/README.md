# Lesson 04 – Singleton Pattern

**ITGreenZone – GoF Design Patterns in Java** · [itgreenzone.com](https://itgreenzone.com)

> **Intent:** make sure a class has only one instance, and give everyone one global way to reach it.

## Scenario

The bank's `ExchangeRateCache` loads FX rates from a slow treasury feed (`RateSource`, which sleeps 20 ms and counts its calls). Payments and reporting both need the rates.

## Packages

| Package    | What it shows |
|------------|---------------|
| `before`   | A public constructor: each service does `new ExchangeRateCache()`, so the feed loads repeatedly and the copies disagree. |
| `after`    | Five classic variants: `Eager`, `Lazy` (**not** thread-safe), `Synchronized`, `DoubleChecked` (with `volatile`) and the **holder idiom** (`ExchangeRateCache`, recommended classic form). |
| `modern`   | The **enum singleton** (`ExchangeRates.INSTANCE`), safe from reflection and serialisation, plus `FxConversionService`, which receives an `ExchangeRateProvider` so it can be tested with a fake (the DI-friendly way). |
| `exercise` | Make `AuditLogger` a thread-safe singleton. |
| `solution` | Holder-idiom `AuditLogger` + a 100-thread concurrency test. |

## How to run

**IntelliJ IDEA:** run `before.BeforeDemo`, then `after.AfterDemo`, and compare the output. To run the tests, right-click `src/test/java` and choose **Run 'All Tests'**.

```bash
mvn test
mvn compile exec:java -Dexec.mainClass=com.itgreenzone.patterns.creational.singleton.before.BeforeDemo
mvn compile exec:java -Dexec.mainClass=com.itgreenzone.patterns.creational.singleton.after.AfterDemo
```

## Exercise (≈20 min)

Make `exercise.AuditLogger` a lazy, thread-safe singleton, then prove it with a test that starts 100 threads. Compare with `solution.AuditLogger` and `AuditLoggerTest`.
