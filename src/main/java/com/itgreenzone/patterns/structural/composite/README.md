# Lesson 11 – Composite Pattern

**ITGreenZone – GoF Design Patterns in Java** · [itgreenzone.com](https://itgreenzone.com)

> **Intent:** compose objects into tree structures to represent part–whole hierarchies, and let clients treat individual objects and compositions uniformly.

## Scenario

An investment portfolio holds **Stocks**, **Bonds** and **Unit Trusts**, and nested sub-portfolios (Retirement → Global Equities → holdings). We need the market value and annual fees at **any** level.

## Packages

| Package    | What it shows |
|------------|---------------|
| `before`   | `PortfolioReport` walks the tree with `instanceof` chains, and copies the recursion for every calculation. |
| `after`    | **Component** `PortfolioComponent`, **Leaves** `Stock`, `Bond`, `UnitTrust`, **Composite** `Portfolio` (sums its children). `add`/`remove` live only on `Portfolio` (the "safety" design). |
| `modern`   | A **sealed** `Asset` tree (`Holding` / `Fund` records) with exhaustive pattern-matching `switch`. It fits because the shape is fixed. |
| `exercise` | Implement a value-weighted `riskScore()` on the composite. |
| `solution` | Reference solution + tests. |

## How to run

**IntelliJ IDEA:** right-click `after.AfterDemo` and choose **Run**. To run the tests, right-click `src/test/java` and choose **Run 'All Tests'**.

```bash
mvn test
mvn compile exec:java -Dexec.mainClass=com.itgreenzone.patterns.structural.composite.after.AfterDemo
```

## Exercise (≈20 min)

Implement `exercise.RiskPortfolio.marketValue()` and `riskScore()` (a value-weighted average), with no `instanceof`. Compare with `solution`.
