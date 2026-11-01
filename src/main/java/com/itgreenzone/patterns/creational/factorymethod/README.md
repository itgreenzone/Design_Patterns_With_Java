# Lesson 05 – Factory Method Pattern

**ITGreenZone – GoF Design Patterns in Java** · [itgreenzone.com](https://itgreenzone.com)

> **Intent:** define a method for creating an object, but let subclasses decide which class to create.

## Scenario

An insurance platform issues **Motor**, **Travel** and **Health** policies. Every product follows the same issuing workflow (validate → create → number → welcome letter); only the policy class differs.

## Packages

| Package    | What it shows |
|------------|---------------|
| `before`   | `PolicyService` with a `switch` that calls `new` for each product, tangled with the workflow. |
| `after`    | **Creator** `PolicyIssuer` (final `issue()` workflow + abstract `createPolicy()`), **Concrete Creators** `Motor/Travel/HealthPolicyIssuer`, **Product** `Policy`, **Concrete Products**. `HomePolicy` + `HomePolicyIssuer` were added later with no change to existing classes. |
| `modern`   | `PolicyFactory`: a registry of `Function<QuoteRequest, Policy>` lambdas. Enough when only the class varies. |
| `exercise` | Add "Annual Multi-Trip" travel cover with its own issuer. |
| `solution` | Reference solution + tests. |

## How to run

**IntelliJ IDEA:** right-click `after.AfterDemo` and choose **Run**. To run the tests, right-click `src/test/java` and choose **Run 'All Tests'**.

```bash
mvn test
mvn compile exec:java -Dexec.mainClass=com.itgreenzone.patterns.creational.factorymethod.after.AfterDemo
```

## Exercise (≈20 min)

Implement `exercise.AnnualMultiTripPolicy` (SGD 280 under age 70, otherwise SGD 450) and `exercise.AnnualMultiTripIssuer`. Do **not** edit `PolicyIssuer`. Compare with `solution`.
