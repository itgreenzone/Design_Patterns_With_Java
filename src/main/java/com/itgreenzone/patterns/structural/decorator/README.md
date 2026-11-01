# Lesson 12 – Decorator Pattern

**ITGreenZone – GoF Design Patterns in Java** · [itgreenzone.com](https://itgreenzone.com)

> **Intent:** attach additional responsibilities to an object dynamically. Decorators are a flexible alternative to subclassing for extending behaviour.

## Scenario

A `PaymentProcessor` must optionally add **logging**, **fraud screening**, **currency conversion** and **retry-on-timeout**, in different combinations for each merchant.

## Packages

| Package    | What it shows |
|------------|---------------|
| `before`   | One `PaymentService` with four boolean flags and if-blocks (the alternative, a subclass per combination, would be 16 classes). |
| `after`    | **Component** `PaymentProcessor`, **Concrete Component** `GatewayPaymentProcessor`, **Decorator** base `PaymentProcessorDecorator`, and **Concrete Decorators** `LoggingDecorator`, `FraudScreenDecorator`, `CurrencyConversionDecorator`, `RetryDecorator`. |
| `modern`   | Decorators as `UnaryOperator<PaymentProcessor>` lambdas, composed with `Decorators.stack(...)`. |
| `exercise` | Write a thread-safe `RateLimitDecorator`. |
| `solution` | Reference solution + tests. |

**Order matters:** if the fraud screen runs *before* currency conversion, USD 4,000 (≈ SGD 5,360) slips under an SGD 5,000 limit. `DecoratorTest.orderOfWrappingMatters` proves it.

## How to run

**IntelliJ IDEA:** right-click `after.AfterDemo` and choose **Run**. To run the tests, right-click `src/test/java` and choose **Run 'All Tests'**.

```bash
mvn test
mvn compile exec:java -Dexec.mainClass=com.itgreenzone.patterns.structural.decorator.after.AfterDemo
```

## Exercise (≈20 min)

Implement `exercise.RateLimitDecorator`. Compare with `solution`.
