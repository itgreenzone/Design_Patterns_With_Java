# Lesson 10 – Bridge Pattern

**ITGreenZone – GoF Design Patterns in Java** · [itgreenzone.com](https://itgreenzone.com)

> **Intent:** decouple an abstraction from its implementation so the two can vary independently.

## Scenario

A bank sends several **alert types** (transaction, fraud, statement-ready) over several **channels** (SMS, email, push). Both lists keep growing.

## Packages

| Package    | What it shows |
|------------|---------------|
| `before`   | One subclass per combination: 3 × 3 = **9** classes (and 4 × 4 = 16 after adding WhatsApp and LowBalance). |
| `after`    | **Abstraction** `Alert` holds a `NotificationChannel` (the bridge). **Refined Abstractions**: `TransactionAlert`, `FraudAlert`, `StatementReadyAlert`. **Implementor**: `NotificationChannel`. **Concrete Implementors**: `SmsChannel`, `EmailChannel`, `PushChannel`. 3 + 3 = 6 classes. |
| `modern`   | Channels as lambdas (`Channels.inApp()`) for quick channels and tests. |
| `exercise` | Add `WhatsAppChannel` and `LowBalanceAlert`: 2 new classes, 16 combinations. |
| `solution` | Reference solution + tests. |

## How to run

**IntelliJ IDEA:** right-click `after.AfterDemo` and choose **Run**. To run the tests, right-click `src/test/java` and choose **Run 'All Tests'**.

```bash
mvn test
mvn compile exec:java -Dexec.mainClass=com.itgreenzone.patterns.structural.bridge.after.AfterDemo
```

## Exercise (≈20 min)

Implement `exercise.WhatsAppChannel` and `exercise.LowBalanceAlert` without changing any existing class. Compare with `solution`.
