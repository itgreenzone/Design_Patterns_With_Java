package com.itgreenzone.patterns.foundations.intro.creational;

import java.math.BigDecimal;

/**
 * What every bank account can do. Callers depend on this interface,
 * never on SavingsAccount or CurrentAccount directly.
 */
public interface Account {

    String accountNumber();

    String owner();

    BigDecimal monthlyFee();
}
