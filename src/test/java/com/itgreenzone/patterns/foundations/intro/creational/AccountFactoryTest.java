package com.itgreenzone.patterns.foundations.intro.creational;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountFactoryTest {

    private final AccountFactory factory = new AccountFactory();

    @Test
    void opensTheRightKindOfAccount() {
        Account savings = factory.open(AccountType.SAVINGS, "Alice Tan");
        Account current = factory.open(AccountType.CURRENT, "Bob Lim");
        assertInstanceOf(SavingsAccount.class, savings);
        assertInstanceOf(CurrentAccount.class, current);
        assertEquals(new BigDecimal("0.00"), savings.monthlyFee());
        assertEquals(new BigDecimal("5.00"), current.monthlyFee());
    }

    @Test
    void accountNumbersAreUniqueAndPrefixed() {
        Account a = factory.open(AccountType.SAVINGS, "Alice Tan");
        Account b = factory.open(AccountType.SAVINGS, "Alice Tan");
        assertTrue(a.accountNumber().startsWith("SA-"));
        assertNotEquals(a.accountNumber(), b.accountNumber());
    }

    @Test
    void rejectsBlankOwner() {
        assertThrows(IllegalArgumentException.class, () -> factory.open(AccountType.CURRENT, " "));
    }
}
