# Lesson 15 – Proxy Pattern

**ITGreenZone – GoF Design Patterns in Java** · [itgreenzone.com](https://itgreenzone.com)

> **Intent:** provide a surrogate or placeholder for another object to control access to it.

## Scenario

`CreditReportService` calls an external credit bureau. Creating the client is expensive (a secure session), each call is slow and costs **SGD 2.50**, and only users with the **UNDERWRITER** role may view full reports.

## Packages

| Package    | What it shows |
|------------|---------------|
| `before`   | `UnderwritingScreen` does its own lazy creation, role check and never-expiring cache; other screens don't. |
| `after`    | **Subject** `CreditReportService`, **Real Subject** `BureauCreditReportService`, and three proxies: **virtual** `LazyCreditReportService`, **protection** `SecuredCreditReportService` and **caching** `CachingCreditReportService` (TTL + injected `Clock`). They stack. |
| `modern`   | `TimingProxy`: a **JDK dynamic proxy** (`java.lang.reflect.Proxy`) that times any interface, which is how Spring AOP proxies work. |
| `exercise` | A caching proxy for FX rates with a configurable TTL. |
| `solution` | Reference solution + a test using a fake clock. |

**Spring gotcha:** `@Transactional` and `@Cacheable` work through proxies, so a method calling *another method on the same bean* (`this.foo()`) bypasses the proxy, and the annotation silently does nothing.

## How to run

**IntelliJ IDEA:** right-click `after.AfterDemo` and choose **Run**. To run the tests, right-click `src/test/java` and choose **Run 'All Tests'**.

```bash
mvn test
mvn compile exec:java -Dexec.mainClass=com.itgreenzone.patterns.structural.proxy.after.AfterDemo
```

## Exercise (≈20 min)

Implement `exercise.CachingExchangeRateService`. Compare with `solution`.
