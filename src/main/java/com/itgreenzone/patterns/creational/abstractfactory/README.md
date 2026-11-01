# Lesson 06 – Abstract Factory Pattern

**ITGreenZone – GoF Design Patterns in Java** · [itgreenzone.com](https://itgreenzone.com)

> **Intent:** provide an interface for creating families of related objects without naming their concrete classes.

## Scenario

A payment platform operates in **Singapore** and **Malaysia**. Each country needs a matching **family** of components:

| Component | Singapore | Malaysia |
|-----------|-----------|----------|
| `PaymentGateway` | PayNow | DuitNow |
| `TaxCalculator` | GST 9% | SST 8% |
| `ReceiptFormatter` | `SGD …` | `RM …` |

## Packages

| Package    | What it shows |
|------------|---------------|
| `before`   | Country `if/else` in three places, and a real bug: `"sg"` mixes a Singapore gateway with Malaysian tax. |
| `after`    | **Abstract Factory** `RegionalPaymentFactory`, **Concrete Factories** `Singapore/MalaysiaPaymentFactory`, **Abstract Products** (gateway, tax, receipt) and the **Client** `CheckoutService`, which sees only interfaces. |
| `modern`   | `RegionalKit`: the whole family as one immutable record, plus a `Supplier` registry by region code. |
| `exercise` | Add the Thailand family (PromptPay, VAT 7%, THB). |
| `solution` | Reference solution + tests. |

**Weakness to remember:** adding a new *product type* (for example `FraudChecker`) means adding a method to `RegionalPaymentFactory`, and so editing **every** factory.

## How to run

**IntelliJ IDEA:** right-click `after.AfterDemo` and choose **Run**. To run the tests, right-click `src/test/java` and choose **Run 'All Tests'**.

```bash
mvn test
mvn compile exec:java -Dexec.mainClass=com.itgreenzone.patterns.creational.abstractfactory.after.AfterDemo
```

## Exercise (≈25 min)

Implement `exercise.ThailandPaymentFactory` and its products. Don't touch `CheckoutService`. Compare with `solution`.
