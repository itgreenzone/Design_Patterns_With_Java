package com.itgreenzone.patterns.behavioral.templatemethod.after;

import com.itgreenzone.patterns.behavioral.templatemethod.Account;
import com.itgreenzone.patterns.behavioral.templatemethod.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Month-end run: three account types, one workflow. */
public class AfterDemo {

    public static void main(String[] args) {
        List<String> outbox = new ArrayList<>();
        LocalDate d = LocalDate.of(2026, 10, 15);

        Account savings = new Account("SA-001", "Nimal Perera", new BigDecimal("5000"),
                List.of(new Transaction(d, "Salary", new BigDecimal("1200")),
                        new Transaction(d.plusDays(1), "ATM", new BigDecimal("-200"))));
        Account card = new Account("CC-002", "Kamala Silva", new BigDecimal("-1000"),
                List.of(new Transaction(d, "Airline ticket", new BigDecimal("-500")),
                        new Transaction(d.plusDays(5), "Payment", new BigDecimal("300"))));
        Account fd = new Account("FD-003", "Ruwan Jayasuriya", new BigDecimal("100000"), List.of());

        System.out.println(new SavingsStatement(outbox).generate(savings).body());
        System.out.println(new CreditCardStatement(outbox).generate(card).body());
        System.out.println(new FixedDepositStatement(outbox).generate(fd).body());
        outbox.forEach(System.out::println);
    }
}
