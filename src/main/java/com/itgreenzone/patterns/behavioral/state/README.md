# Lesson 23 – State Pattern

**ITGreenZone – GoF Design Patterns in Java** · [itgreenzone.com](https://itgreenzone.com)

> **Intent:** allow an object to alter its behaviour when its internal state changes. The object will appear to change its class.

## Scenario

A payment order lifecycle: **NEW → PAID → SHIPPED → DELIVERED**. **CANCELLED** is possible from NEW or PAID (a full refund is triggered if already paid). There is no cancellation after SHIPPED.

```
NEW ──pay──▶ PAID ──ship──▶ SHIPPED ──deliver──▶ DELIVERED
 │            │
 └─cancel─▶ CANCELLED ◀─cancel (refund)─┘
```

## Packages

| Package    | What it shows |
|------------|---------------|
| `before`   | A status enum and a `switch` in every method. `cancel()` wrongly allows SHIPPED. |
| `after`    | **Context** `PaymentOrder`, **State** `OrderState` (every event illegal by default), **Concrete States** `NewState`, `PaidState`, `ShippedState`, `DeliveredState`, `CancelledState`, each choosing the next state. |
| `modern`   | `OrderStatus` enum where each constant overrides its legal events, with an `EnumOrder` context. |
| `exercise` | Add a RETURNED state reachable from DELIVERED within 14 days. |
| `solution` | Reference solution + tests (every legal and illegal transition). |

**State vs Strategy (see Lesson 24):** the class diagrams look almost identical, so ask who decides the switch. In Strategy, the client chooses the behaviour and it stays until replaced. In State, the object changes its own behaviour as its state changes, and a state often picks the next state.

Money uses `BigDecimal` at 2 dp, `HALF_UP`.

## How to run

**IntelliJ IDEA:** right-click `after.AfterDemo` and choose **Run**. To run the tests, right-click `src/test/java` and choose **Run 'All Tests'**.

```bash
mvn test
mvn compile exec:java -Dexec.mainClass=com.itgreenzone.patterns.behavioral.state.after.AfterDemo
```

## Exercise (≈20 min)

Implement `exercise.ReturnableOrder.requestReturn(...)`. Compare with `solution`.
