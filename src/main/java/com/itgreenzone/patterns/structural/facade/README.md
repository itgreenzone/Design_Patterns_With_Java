# Lesson 13 – Facade Pattern

**ITGreenZone – GoF Design Patterns in Java** · [itgreenzone.com](https://itgreenzone.com)

> **Intent:** provide one simple, unified interface to a set of interfaces in a subsystem, so the subsystem is easier to use.

## Scenario

Booking a flight touches six subsystems: `SeatInventory`, `FareCalculator`, `PaymentGateway`, `LoyaltyService`, `TicketIssuer` and `NotificationService` (all in `subsystem`).

## Packages

| Package     | What it shows |
|-------------|---------------|
| `subsystem` | The six subsystem classes. They're unchanged, and still usable directly. |
| `before`    | `MobileBookingController` orchestrates all six, in the right order, with its own rollback when payment fails. Every other channel copies it. |
| `after`     | **Facade** `FlightBookingFacade` with `bookFlight(...)` and `cancelBooking(...)`. The order and the failure path (payment declined → seat released) are handled once. |
| `exercise`  | Add `changeFlight(...)` to the facade. |
| `solution`  | Reference solution + tests. |

A modern variant isn't needed here: records are already used for `BookingRequest` and `BookingResult`, and a facade's value is the orchestration itself, which stays a plain class.

## How to run

**IntelliJ IDEA:** right-click `after.AfterDemo` and choose **Run**. To run the tests, right-click `src/test/java` and choose **Run 'All Tests'**.

```bash
mvn test
mvn compile exec:java -Dexec.mainClass=com.itgreenzone.patterns.structural.facade.after.AfterDemo
```

## Exercise (≈25 min)

Implement `exercise.ChangeFlightFacade.changeFlight(...)`. Compare with `solution`.
