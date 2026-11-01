# Lesson 18 – Interpreter Pattern

**ITGreenZone – GoF Design Patterns in Java** · [itgreenzone.com](https://itgreenzone.com)

> **Intent:** given a simple language, represent its grammar as classes and use them to interpret sentences in that language.

## Scenario

Insurance underwriters want to change eligibility rules **without a code release**, e.g.

```
age >= 18 AND age <= 65 AND (country == SG OR country == MY)
```

Convention: lowercase names are facts from the applicant (`age`, `country`); UPPERCASE names are text constants (`SG`).

## Packages

| Package    | What it shows |
|------------|---------------|
| `before`   | `EligibilityService`: the rule is hard-coded Java, so every change needs a release. |
| `after`    | **Abstract Expressions** `Rule` / `ValueExpression`, **Terminal Expressions** `Variable`, `Constant`, **Nonterminal Expressions** `Comparison`, `And`, `Or`, `Not`, **Context** `ApplicantContext`, and a small recursive-descent `RuleParser` (text → tree). |
| `modern`   | `Expr`: a **sealed** hierarchy of records + an exhaustive pattern-matching `switch`. It fits because the grammar is fixed. |
| `exercise` | Add an `IN` operator. |
| `solution` | Reference solution + tests. |

**Be honest about limits:** this is fine for a small rule language. For anything bigger, use a parser generator (ANTLR) or an existing engine (Spring SpEL, Drools, a rules service), with versioning, permissions and auditing for rule changes.

## How to run

**IntelliJ IDEA:** right-click `after.AfterDemo` and choose **Run**. To run the tests, right-click `src/test/java` and choose **Run 'All Tests'**.

```bash
mvn test
mvn compile exec:java -Dexec.mainClass=com.itgreenzone.patterns.behavioral.interpreter.after.AfterDemo
```

## Exercise (≈25 min)

Implement `exercise.InExpression` (bonus: teach the parser `IN [..]`). Compare with `solution`.
