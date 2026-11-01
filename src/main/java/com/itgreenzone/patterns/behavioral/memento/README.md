# Lesson 21 – Memento Pattern

**ITGreenZone – GoF Design Patterns in Java** · [itgreenzone.com](https://itgreenzone.com)

> **Intent:** without violating encapsulation, capture an object's internal state so that it can be restored to that state later.

## Scenario

A personal-loan application wizard (Applicant → Loan details → Review) needs a working **Back** button. Each step must be restorable exactly, including fields added later, without exposing the form's internals.

## Packages

| Package    | What it shows |
|------------|---------------|
| `before`   | The wizard copies fields one by one through public setters, keeps only one level, and forgot the new `purpose` field. |
| `after`    | **Originator** `LoanApplication`, **Memento** `LoanApplication.Snapshot` (immutable, no public accessors), **Caretaker** `WizardHistory` (a stack it never looks inside). |
| `modern`   | An immutable `FormState` record as the snapshot, plus a generic `BoundedHistory<T>` with a capacity limit. |
| `exercise` | Add redo ("Next") with a second stack. |
| `solution` | Reference solution + tests. |

Money uses `BigDecimal` at 2 dp, `HALF_UP`.

## How to run

**IntelliJ IDEA:** right-click `after.AfterDemo` and choose **Run**. To run the tests, right-click `src/test/java` and choose **Run 'All Tests'**.

```bash
mvn test
mvn compile exec:java -Dexec.mainClass=com.itgreenzone.patterns.behavioral.memento.after.AfterDemo
```

## Exercise (≈20 min)

Implement `back` and `forward` in `exercise.RedoableHistory`. Compare with `solution`.
