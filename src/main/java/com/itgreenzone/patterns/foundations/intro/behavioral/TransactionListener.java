package com.itgreenzone.patterns.foundations.intro.behavioral;

/** Anything that wants to hear about new transactions. */
public interface TransactionListener {

    void onTransaction(Transaction transaction);
}
