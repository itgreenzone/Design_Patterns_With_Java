# Lesson 17 – Command Pattern

**ITGreenZone – GoF Design Patterns in Java** · [itgreenzone.com](https://itgreenzone.com)

> **Intent:** encapsulate a request as an object, so you can queue requests, log them and support undoable operations.

## Scenario

A bank teller system: **Deposit**, **Withdraw** and **Transfer** must be queued, audit-logged and reversible (undo for teller mistakes before end of day).

## Packages

| Package    | What it shows |
|------------|---------------|
| `before`   | `TellerService` calls `Account` directly: no history, no undo, audit code repeated in every method. |
| `after`    | **Command** `BankCommand` (`execute`/`undo`), **Concrete Commands** `Deposit/Withdraw/TransferCommand`, **Receiver** `Account`, **Invoker** `TellerTerminal` (history, queue, audit) and an all-or-nothing **MacroCommand** for a salary run. |
| `modern`   | `Commands.of(description, doIt, undoIt)`: a command from two lambdas. |
| `exercise` | `ChargeFeeCommand` with undo. |
| `solution` | Reference solution + tests. |

## How to run

**IntelliJ IDEA:** right-click `after.AfterDemo` and choose **Run**. To run the tests, right-click `src/test/java` and choose **Run 'All Tests'**.

```bash
mvn test
mvn compile exec:java -Dexec.mainClass=com.itgreenzone.patterns.behavioral.command.after.AfterDemo
```

## Exercise (≈20 min)

Implement `exercise.ChargeFeeCommand`. Compare with `solution`.
