# Lesson 24 – Strategy Pattern

**ITGreenZone – GoF Design Patterns in Java** · [itgreenzone.com](https://itgreenzone.com)

> **Intent:** Define a family of algorithms, put each one in its own class, and make them interchangeable, so the code that uses them doesn't change when you add or swap one.

## Scenario

A payment checkout supports **Credit Card**, **PayPal** and **GrabPay**, and the business now wants **PayNow**.

| Method      | Fee                 | Customer identifier         |
|-------------|---------------------|-----------------------------|
| Credit Card | 2.5%                | 16-digit card number        |
| PayPal      | 3.4% + SGD 0.50     | Email address               |
| GrabPay     | 1.5%                | Singapore mobile (+65 8/9…) |
| PayNow      | Free                | Mobile number or UEN        |

## Packages

| Package     | What it shows |
|-------------|---------------|
| `before`    | A single `PaymentService` with a `switch` on `PaymentType`. Every new method means editing it. |
| `after`     | The classic GoF structure. **Context** = `PaymentService`, **Strategy** = `PaymentStrategy`, **Concrete Strategies** = `CreditCardPayment`, `PayPalPayment`, `GrabPayPayment`, plus `PayNowPayment`, which was added with **no change** to the Context. |
| `modern`    | A Java 21 variant: `FeeStrategy` is a `@FunctionalInterface`, so strategies are lambdas, and `PaymentMethod` / `Receipt` are records. |
| `exercise`  | Hands-on task: implement `BuyNowPayLaterPayment` (see the TODOs). |
| `solution`  | The reference solution for the exercise. |

> **Why no sealed interface?** A sealed hierarchy is *closed* by design: adding PayNow would mean editing the `permits` list and every `switch`. That works against Strategy's Open/Closed goal. Sealed types are a better fit when the set of cases is fixed.

## How to run

**IntelliJ IDEA:** open the project root (`pom.xml`), then right-click any `*Demo` class and choose **Run**. To run the tests, right-click `src/test/java` and choose **Run 'All Tests'**.

**Command line:**

```bash
mvn test
mvn compile exec:java -Dexec.mainClass=com.itgreenzone.patterns.behavioral.strategy.before.BeforeDemo
mvn compile exec:java -Dexec.mainClass=com.itgreenzone.patterns.behavioral.strategy.after.AfterDemo
mvn compile exec:java -Dexec.mainClass=com.itgreenzone.patterns.behavioral.strategy.modern.ModernDemo
```

## Exercise: Buy Now, Pay Later (≈20 min)

Implement `exercise.BuyNowPayLaterPayment` as a new Concrete Strategy **without editing `PaymentService`**:

- `name()` returns `"BNPL"`. The fee is `0.00` and the reference starts with `BNPL-`.
- Only orders from SGD 50.00 to SGD 3,000.00 are allowed. Anything else throws `IllegalArgumentException`.
- `installment(amount)` returns `amount / 3`, rounded HALF_UP to 2 decimal places.

Check your work against `solution.BuyNowPayLaterPayment` and its tests.
