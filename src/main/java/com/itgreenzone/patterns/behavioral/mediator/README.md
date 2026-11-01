# Lesson 20 – Mediator Pattern

**ITGreenZone – GoF Design Patterns in Java** · [itgreenzone.com](https://itgreenzone.com)

> **Intent:** define an object that encapsulates how a set of objects interact, so they don't refer to each other directly.

## Scenario

Airport turnaround operations: when a **Flight** is delayed, the **Gate**, **GroundCrew**, **Catering** and **PassengerNotifier** must all react. Some reactions trigger others: a long delay causes a gate change, which in turn moves the crew and informs passengers.

## Packages

| Package    | What it shows |
|------------|---------------|
| `before`   | Components call each other directly. `Flight` knows four other classes, and the rules are spread across them (N×(N−1) potential links). |
| `after`    | **Mediator** `OperationsControl`, **Concrete Mediator** `TurnaroundCoordinator`, and **Colleagues** (`Flight`, `Gate`, `GroundCrew`, `Catering`, `PassengerNotifier`) that know only the mediator. |
| `modern`   | `OpsEvent` sealed records + `EventCoordinator.publish(...)` with an exhaustive `switch`. |
| `exercise` | Add a fuel-truck colleague and a "fuel delay" event, changing only the mediator. |
| `solution` | Reference solution + tests. |

**Watch out:** a mediator can grow into a "God object". Keep it to coordination, and split it by area (turnaround, boarding, baggage) as it grows.

## How to run

**IntelliJ IDEA:** right-click `after.AfterDemo` and choose **Run**. To run the tests, right-click `src/test/java` and choose **Run 'All Tests'**.

```bash
mvn test
mvn compile exec:java -Dexec.mainClass=com.itgreenzone.patterns.behavioral.mediator.after.AfterDemo
```

## Exercise (≈20 min)

Implement `exercise.FuelAwareCoordinator.fuelDelayed(...)`. Compare with `solution`.
