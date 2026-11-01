# Lesson 03 – Exercise solution: diagrams for `RefundService`

## Class diagram

```mermaid
classDiagram
    direction LR
    class RefundService {
      -PaymentService payments
      +refund(Booking, LocalDate, LocalDate) RefundResult
    }
    class PaymentService {
      <<interface>>
      +charge(BigDecimal) String
      +refund(String, BigDecimal) String
    }
    class RefundResult {
      <<record>>
      +amount BigDecimal
      +refundRef String
    }
    class Booking {
      <<record>>
      +total BigDecimal
      +paymentRef String
    }
    RefundService --> "1" PaymentService : payments
    RefundService ..> Booking : reads
    RefundService ..> RefundResult : creates
```

## Sequence diagram

```mermaid
sequenceDiagram
    participant C as Client
    participant R as RefundService
    participant P as PaymentService
    C->>R: refund(booking, today, departure)
    R->>R: days = between(today, departure)
    alt days >= 7
        R->>P: refund(paymentRef, 100% of total)
        P-->>R: refundRef
    else days >= 1
        R->>P: refund(paymentRef, 50% of total)
        P-->>R: refundRef
    else departure day or later
        Note over R: no call to PaymentService
    end
    R-->>C: RefundResult
```

The test `solution.RefundServiceTest` proves the code behaves exactly as these diagrams describe.
