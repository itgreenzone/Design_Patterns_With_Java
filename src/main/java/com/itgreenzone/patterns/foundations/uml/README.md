# Lesson 03 – Reading UML Class and Sequence Diagrams

**ITGreenZone – GoF Design Patterns in Java** · [itgreenzone.com](https://itgreenzone.com)

> UML is how every later lesson shows a pattern's structure. This lesson's Java code matches its diagrams **one to one**.

## Scenario

An airline booking system: `Flight`, `Seat`, `Passenger`, `LoyaltyAccount`, `Fare`, `Booking`, `PaymentService` and `BookingService`.

## Where each UML relationship lives in the code

| UML | Symbol | Java in this package |
|-----|--------|----------------------|
| Association (0..1) | `──>` | `Passenger` → `LoyaltyAccount` (field may be null) |
| Aggregation (1..*) | `◇──` | `Booking` groups `Passenger`s that exist independently |
| Composition (1..*) | `◆──` | `Flight` creates and owns its `Seat`s |
| Inheritance | `──▷` | `EconomyFare` / `BusinessFare` extend `Fare` |
| Realisation | `- -▷` | `CardPaymentService` implements `PaymentService` |
| Dependency | `- ->` | `BookingService` uses `BookingReferences` only inside a method |
| Visibility | `+ - # ~` | `Fare.baseAmount` is `#` (protected); `Seat.book()` is `~` (package-private) |

`BookingService.book(...)` follows the sequence diagram step by step: **loop** over the seats, **alt** when a seat is taken, **opt** when a passenger has a loyalty account.

## How to run

**IntelliJ IDEA:** right-click `UmlDemo` and choose **Run**. You can also right-click the package and choose **Diagrams → Show Diagram** to have IntelliJ draw the class diagram for you.

```bash
mvn test
mvn compile exec:java -Dexec.mainClass=com.itgreenzone.patterns.foundations.uml.UmlDemo
```

## Exercise (≈20 min)

This time the code is given (`exercise.RefundService`) and **you draw the diagrams**: a Mermaid class diagram and a sequence diagram with an `alt` fragment for the three refund bands. Check them at [mermaid.live](https://mermaid.live), then compare with `solution/README.md`.
