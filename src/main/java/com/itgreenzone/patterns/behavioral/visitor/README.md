# Lesson 26 – Visitor Pattern

**ITGreenZone – GoF Design Patterns in Java** · [itgreenzone.com](https://itgreenzone.com)

> **Intent:** represent an operation to be performed on the elements of an object structure, so you can add new operations without changing the classes of the elements.

## Scenario

An insurance portfolio holds **MotorPolicy**, **TravelPolicy** and **HealthPolicy** objects. The business keeps adding operations: premium calculation, risk report, regulatory (MAS) export, renewal reminders. It doesn't want to edit every policy class each time.

## Packages

| Package    | What it shows |
|------------|---------------|
| `before`   | Each operation is a method on every policy class (`premium()`, `toMasCsv()`), or an `instanceof` chain that silently forgets a type. |
| `after`    | **Visitor** `PolicyVisitor<R>` (`visitMotor` / `visitTravel` / `visitHealth`), **Concrete Visitors** `PremiumCalculator`, `RiskReportVisitor`, `RegulatoryExportVisitor`, **Element** `InsurancePolicy.accept()`, **Concrete Elements**, **Object Structure** `PolicyPortfolio`. |
| `modern`   | `sealed interface Policy` with record variants + exhaustive pattern-matching `switch`. Sealed fits here because the policy types are fixed and the operations vary (the opposite of Lesson 24). |
| `exercise` | Add a `RenewalReminderVisitor`. |
| `solution` | Reference solution + tests. |

**Double dispatch:** `policy.accept(visitor)` dispatches on the policy's runtime type; inside, `visitor.visitMotor(this)` dispatches on the visitor's runtime type. Two dispatches choose the right code for (policy type × operation).

**Trade-off:** adding an operation is easy (one new visitor). Adding a policy type is hard (a new method in every visitor).

Money uses `BigDecimal` at 2 dp, `HALF_UP`.

## How to run

**IntelliJ IDEA:** right-click `after.AfterDemo` and choose **Run**. To run the tests, right-click `src/test/java` and choose **Run 'All Tests'**.

```bash
mvn test
mvn compile exec:java -Dexec.mainClass=com.itgreenzone.patterns.behavioral.visitor.after.AfterDemo
```

## Exercise (≈20 min)

Implement `exercise.RenewalReminderVisitor`. Compare with `solution`.
