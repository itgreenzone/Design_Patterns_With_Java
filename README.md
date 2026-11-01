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

| #  | Lesson | Category | Package |
|----|--------|----------|---------|
| 01 | Intro & GoF Categories | Foundations | [`foundations.intro`](src/main/java/com/itgreenzone/patterns/foundations/intro) |
| 02 | OOP & SOLID | Foundations | [`foundations.solid`](src/main/java/com/itgreenzone/patterns/foundations/solid) |
| 03 | Reading UML | Foundations | [`foundations.uml`](src/main/java/com/itgreenzone/patterns/foundations/uml) |
| 04 | Singleton | Creational | [`creational.singleton`](src/main/java/com/itgreenzone/patterns/creational/singleton) |
| 24 | Strategy | Behavioral | [`behavioral.strategy`](src/main/java/com/itgreenzone/patterns/behavioral/strategy) |

## Build & test

```bash
mvn test
```
