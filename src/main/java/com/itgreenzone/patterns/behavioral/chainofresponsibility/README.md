# Lesson 16 – Chain of Responsibility Pattern

**ITGreenZone – GoF Design Patterns in Java** · [itgreenzone.com](https://itgreenzone.com)

> **Intent:** avoid coupling the sender of a request to its receiver by giving more than one object a chance to handle it. Chain the receivers and pass the request along until one handles it.

## Scenario

Loan approval routing by authority limit: **Loan Officer** up to SGD 50,000, **Branch Manager** up to 250,000, **Regional Director** up to 1,000,000, and the **Credit Committee** above that. Later, a **Risk Officer** must review high-risk applicants.

## Packages

| Package    | What it shows |
|------------|---------------|
| `before`   | `LoanApprovalService` with an if/else ladder over every level. |
| `after`    | **Handler** `LoanApprover` (`setNext` / final `approve`), **Concrete Handlers** `LimitApprover`, `CreditCommittee`, `RiskOfficer` (added at the front with no other changes), and the chain assembly in `ApprovalChains`. |
| `after.validation` | A second style: a **pipeline** where EVERY check runs (KYC → blacklist → income) and all problems are collected. |
| `modern`   | The chain as a `List<Function<LoanApplication, Optional<Decision>>>`: the first non-empty answer wins. |
| `exercise` | A fraud-escalation handler that jumps straight to the committee. |
| `solution` | Reference solution + tests. |

## How to run

**IntelliJ IDEA:** right-click `after.AfterDemo` and choose **Run**. To run the tests, right-click `src/test/java` and choose **Run 'All Tests'**.

```bash
mvn test
mvn compile exec:java -Dexec.mainClass=com.itgreenzone.patterns.behavioral.chainofresponsibility.after.AfterDemo
```

## Exercise (≈20 min)

Implement `exercise.FraudEscalationHandler`. Compare with `solution`.
