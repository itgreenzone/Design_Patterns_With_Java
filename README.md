# Design Patterns With Java

Course code for **ITGreenZone – Gang of Four (GoF) Design Patterns in Java** · [itgreenzone.com](https://itgreenzone.com)

One Maven project (Java 21, JUnit 5) that opens directly in IntelliJ IDEA. Each pattern lives in its own package:

```
com.itgreenzone.patterns.<category>.<pattern>
    ├── before     code WITHOUT the pattern (the pain)
    ├── after      the same code refactored WITH the pattern
    ├── modern     Java 21 variant (where it improves clarity)
    ├── exercise   hands-on task with TODOs
    └── solution   reference solution
```

Each pattern folder has its own `README.md` describing the scenario and how to run it.

## Lessons

| #  | Pattern  | Category   | Package |
|----|----------|------------|---------|
| 24 | Strategy | Behavioral | [`behavioral.strategy`](src/main/java/com/itgreenzone/patterns/behavioral/strategy) |

More lessons are added as the course progresses.

## Build & test

```bash
mvn test
```
