# Lesson 01 – What Design Patterns Are + the Three GoF Categories

**ITGreenZone – GoF Design Patterns in Java** · [itgreenzone.com](https://itgreenzone.com)

> A **design pattern** is a named, proven solution to a design problem that keeps coming up. It is a reusable idea, not a library and not code to copy and paste.

## Scenario

A growing retail-banking app (accounts, transfers, alerts) is getting hard to change. This lesson shows one tiny preview from each GoF category, all in that banking app.

## Packages

| Package       | Category   | What it shows | Full lesson |
|---------------|------------|---------------|-------------|
| `creational`  | Creational | `AccountFactory`: one place decides how accounts are created | L05 Factory Method |
| `structural`  | Structural | `CoreBankingAdapter`: wraps a legacy API so it fits our interface | L09 Adapter |
| `behavioral`  | Behavioral | `TransactionNotifier`: alerts subscribe themselves to new transactions | L22 Observer |
| `exercise`    | –          | Classify 8 design problems into the three categories | |
| `solution`    | –          | Reference answers (and the pattern behind each one) | |

## How to run

**IntelliJ IDEA:** open the project root (`pom.xml`), then right-click `IntroDemo` and choose **Run**. To run the tests, right-click `src/test/java` and choose **Run 'All Tests'**.

```bash
mvn test
mvn compile exec:java -Dexec.mainClass=com.itgreenzone.patterns.foundations.intro.IntroDemo
```

## Exercise (≈15 min)

Implement `exercise.CategoryQuiz.classify(...)` for the 8 problems in `DesignProblem.ALL`. For each one, decide whether it is about **creating** objects, **composing** them, or how they **communicate**. Then compare with `solution.CategoryAnswers` and `CategoryAnswersTest`.
