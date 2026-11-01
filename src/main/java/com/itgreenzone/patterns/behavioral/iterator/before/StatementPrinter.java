package com.itgreenzone.patterns.behavioral.iterator.before;

import com.itgreenzone.patterns.behavioral.iterator.Transaction;
import com.itgreenzone.patterns.behavioral.iterator.TransactionPageApi;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

/**
 * BEFORE: every caller writes its own page loop + index loop, so each one knows
 * that history is stored in monthly pages. The debit report copies the same loops.
 */
public class StatementPrinter {

    public List<String> lines(TransactionPageApi api, YearMonth from, YearMonth to) {
        List<String> out = new ArrayList<>();
        for (YearMonth m = from; !m.isAfter(to); m = m.plusMonths(1)) {
            List<Transaction> page = api.fetchPage(m);
            for (int i = 0; i < page.size(); i++) {
                Transaction t = page.get(i);
                out.add(t.date() + " " + t.description() + " " + t.amount());
            }
        }
        return out;
    }

    public List<Transaction> largeDebits(TransactionPageApi api, YearMonth from, YearMonth to) {
        List<Transaction> out = new ArrayList<>();
        for (YearMonth m = from; !m.isAfter(to); m = m.plusMonths(1)) {        // copied loop
            List<Transaction> page = api.fetchPage(m);
            for (int i = 0; i < page.size(); i++) {
                if (page.get(i).amount().compareTo(new BigDecimal("-1000")) <= 0) {
                    out.add(page.get(i));
                }
            }
        }
        return out;
    }
}
