package com.itgreenzone.patterns.behavioral.iterator.after;

import com.itgreenzone.patterns.behavioral.iterator.Transaction;
import com.itgreenzone.patterns.behavioral.iterator.TransactionPageApi;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Iterator;
import java.util.List;

/** for-each over three months of history; the caller never sees a page. */
public class AfterDemo {

    public static void main(String[] args) {
        TransactionPageApi api = new TransactionPageApi();
        api.put(YearMonth.of(2026, 8), List.of(new Transaction("T1", LocalDate.of(2026, 8, 3), new BigDecimal("-45.20"), "NTUC")));
        api.put(YearMonth.of(2026, 10), List.of(new Transaction("T2", LocalDate.of(2026, 10, 1), new BigDecimal("5200.00"), "Salary"),
                new Transaction("T3", LocalDate.of(2026, 10, 5), new BigDecimal("-2400.00"), "Rent")));

        TransactionHistory history = new TransactionHistory(api, YearMonth.of(2026, 8), YearMonth.of(2026, 10));
        for (Transaction t : history) {
            System.out.println(t.date() + "  " + t.description() + "  " + t.amount());
        }
        Iterator<Transaction> big = new FilteringIterator<>(history.iterator(), t -> t.amount().compareTo(new BigDecimal("-1000")) <= 0);
        big.forEachRemaining(t -> System.out.println("Large debit: " + t.description()));
    }
}
