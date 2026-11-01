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
| 05 | Factory Method | Creational | [`creational.factorymethod`](src/main/java/com/itgreenzone/patterns/creational/factorymethod) |
| 06 | Abstract Factory | Creational | [`creational.abstractfactory`](src/main/java/com/itgreenzone/patterns/creational/abstractfactory) |
| 07 | Builder | Creational | [`creational.builder`](src/main/java/com/itgreenzone/patterns/creational/builder) |
| 08 | Prototype | Creational | [`creational.prototype`](src/main/java/com/itgreenzone/patterns/creational/prototype) |
| 09 | Adapter | Structural | [`structural.adapter`](src/main/java/com/itgreenzone/patterns/structural/adapter) |
| 10 | Bridge | Structural | [`structural.bridge`](src/main/java/com/itgreenzone/patterns/structural/bridge) |
| 11 | Composite | Structural | [`structural.composite`](src/main/java/com/itgreenzone/patterns/structural/composite) |
| 12 | Decorator | Structural | [`structural.decorator`](src/main/java/com/itgreenzone/patterns/structural/decorator) |
| 13 | Facade | Structural | [`structural.facade`](src/main/java/com/itgreenzone/patterns/structural/facade) |
| 14 | Flyweight | Structural | [`structural.flyweight`](src/main/java/com/itgreenzone/patterns/structural/flyweight) |
| 15 | Proxy | Structural | [`structural.proxy`](src/main/java/com/itgreenzone/patterns/structural/proxy) |
| 16 | Chain of Responsibility | Behavioral | [`behavioral.chainofresponsibility`](src/main/java/com/itgreenzone/patterns/behavioral/chainofresponsibility) |
| 17 | Command | Behavioral | [`behavioral.command`](src/main/java/com/itgreenzone/patterns/behavioral/command) |
| 24 | Strategy | Behavioral | [`behavioral.strategy`](src/main/java/com/itgreenzone/patterns/behavioral/strategy) |

## Build & test

```bash
mvn test
```
