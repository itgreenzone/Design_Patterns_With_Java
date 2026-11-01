package com.itgreenzone.patterns.foundations.intro.behavioral;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * BEHAVIORAL preview: the notifier does not know WHO is listening.
 *
 * New channels (push, WhatsApp) subscribe themselves; this class never
 * changes. (Full pattern: Lesson 22 – Observer.)
 */
public class TransactionNotifier {

    private final List<TransactionListener> listeners = new CopyOnWriteArrayList<>();

    public void subscribe(TransactionListener listener) {
        listeners.add(listener);
    }

    public void publish(Transaction transaction) {
        listeners.forEach(l -> l.onTransaction(transaction));
    }
}
