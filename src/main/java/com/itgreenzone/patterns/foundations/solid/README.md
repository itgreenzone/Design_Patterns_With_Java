# Lesson 02 – OOP and SOLID Refresher

**ITGreenZone – GoF Design Patterns in Java** · [itgreenzone.com](https://itgreenzone.com)

> SOLID is five design principles that make code easier to change. Almost every GoF pattern is one of these principles put into practice.

## Scenario

An insurance **claims-processing** module. `Claim` and `ClaimType` (in this package) are shared by every example.

## Packages

| Package | Principle | Before | After | Patterns that rely on it |
|---------|-----------|--------|-------|--------------------------|
| `srp` | **S**ingle Responsibility | `ClaimService` validates, pays **and** emails | Validator, Calculator, Notifier + a coordinating service | Facade, Command |
| `ocp` | **O**pen/Closed | `switch` on claim type | `PayoutRule` per product | Strategy, Decorator, Observer |
| `lsp` | **L**iskov Substitution | `ReadOnlyClaimFile` throws on `approve()` | `ClaimView` / `Approvable` split | Composite, Template Method |
| `isp` | **I**nterface Segregation | Fat `ClaimRepository` | `ClaimReader` + `ClaimWriter` | Adapter, Proxy |
| `dip` | **D**ependency Inversion | `new SmtpClient()` inside | `EmailSender` injected | Factory Method, Abstract Factory |
| `exercise` / `solution` | SRP + DIP | `PolicyRenewalService` | `RenewalService` | |

## How to run

**IntelliJ IDEA:** open the project root (`pom.xml`). To run the tests, right-click `src/test/java/.../foundations/solid` and choose **Run Tests**.

```bash
mvn test
```

## Exercise (≈25 min)

`exercise.PolicyRenewalService` works, but it breaks SRP (it prices, words **and** sends the letter) and DIP (it uses `new SmtpClient()`).

1. Implement `RenewalPremiumCalculator` (5% no-claim discount, otherwise a 10% loading) and `RenewalLetterFormatter`.
2. Write a `RenewalService` that receives both of them, plus an `EmailSender`, through its constructor.
3. Test it with a fake `EmailSender` (no mail server needed). Compare with `solution`.
