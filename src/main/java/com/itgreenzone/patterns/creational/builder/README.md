# Lesson 07 – Builder Pattern

**ITGreenZone – GoF Design Patterns in Java** · [itgreenzone.com](https://itgreenzone.com)

> **Intent:** separate the construction of a complex object from its representation, so the same building steps can create different results. In practice: build it step by step, readably, and get back a valid, immutable object.

## Scenario

A `LoanApplication` has 3 required fields (applicant, amount, tenure) and 6 optional ones (co-applicant, collateral, employer, promo code, insurance add-on, disbursement account). Rules: tenure must be 12–360 months, and collateral is required above SGD 500,000.

## Packages

| Package    | What it shows |
|------------|---------------|
| `before`   | Telescoping constructors (unreadable, easy to swap arguments) and a JavaBean with setters (mutable, can be half-built, no validation). |
| `after`    | Static nested **Builder** with a fluent API, `build()` validation and an immutable **Product**, plus the classic **Director** (`LoanApplicationDirector`) with Home/Car loan recipes. |
| `modern`   | A **record** with a nested builder; the compact constructor validates. |
| `exercise` | Write a builder for `InsuranceQuoteRequest`. |
| `solution` | Reference solution + tests. |

## How to run

**IntelliJ IDEA:** right-click `after.AfterDemo` and choose **Run**. To run the tests, right-click `src/test/java` and choose **Run 'All Tests'**.

```bash
mvn test
mvn compile exec:java -Dexec.mainClass=com.itgreenzone.patterns.creational.builder.after.AfterDemo
```

## Exercise (≈25 min)

Implement the builder in `exercise.InsuranceQuoteRequest` (defaults, validation and an unmodifiable riders list). Compare with `solution`.
