# Lesson 25 – Template Method Pattern

**ITGreenZone – GoF Design Patterns in Java** · [itgreenzone.com](https://itgreenzone.com)

> **Intent:** define the skeleton of an algorithm in a method, deferring some steps to subclasses, so subclasses can redefine certain steps without changing the algorithm's structure.

## Scenario

Month-end bank statements for **savings**, **credit card** and **fixed deposit** accounts. Each follows the same steps: load transactions → calculate interest or charges → apply fees → (promotions) → format → deliver.

## Packages

| Package    | What it shows |
|------------|---------------|
| `before`   | Three generators with the workflow copy-pasted. A rounding fix (HALF_UP) never reached the credit-card copy. |
| `after`    | **Abstract class** `StatementGenerator` with a **final** `generate()` template method; **primitive operations** `calculateInterest()` and `format()`; **hooks** `monthlyFee()`, `applyPromotions()`, `extraSection()` and `channel()`; **concrete classes** `SavingsStatement`, `CreditCardStatement`, `FixedDepositStatement`. |
| `modern`   | `ComposedStatementGenerator`: the same skeleton with the steps injected as lambdas (composition), like Spring's `JdbcTemplate`. |
| `exercise` | Add a `LoanStatement` whose amortisation step plugs in via the `extraSection` hook. |
| `solution` | Reference solution + tests. |

**Hollywood Principle:** "don't call us, we'll call you". Subclasses never call the workflow; `generate()` calls their steps. `generate()` is `final` so no subclass can reorder or skip steps.

Money uses `BigDecimal` at 2 dp, `HALF_UP`.

## How to run

**IntelliJ IDEA:** right-click `after.AfterDemo` and choose **Run**. To run the tests, right-click `src/test/java` and choose **Run 'All Tests'**.

```bash
mvn test
mvn compile exec:java -Dexec.mainClass=com.itgreenzone.patterns.behavioral.templatemethod.after.AfterDemo
```

## Exercise (≈20 min)

Implement `exercise.LoanStatement`. Compare with `solution`.
