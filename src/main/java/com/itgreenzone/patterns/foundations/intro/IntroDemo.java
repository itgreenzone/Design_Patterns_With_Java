package com.itgreenzone.patterns.foundations.intro;

import com.itgreenzone.patterns.foundations.intro.behavioral.*;
import com.itgreenzone.patterns.foundations.intro.creational.*;
import com.itgreenzone.patterns.foundations.intro.structural.*;

import java.math.BigDecimal;
import java.util.Map;

/** Run me: one tiny example from each GoF category. */
public class IntroDemo {

    public static void main(String[] args) {
        // Creational – HOW objects are created
        Account account = new AccountFactory().open(AccountType.SAVINGS, "Alice Tan");
        System.out.println("Opened " + account);

        // Structural – HOW objects are composed / wrapped
        AccountBalanceService balances = new CoreBankingAdapter(
                new LegacyCoreBankingSystem(Map.of(account.accountNumber(), 250_075L)));
        System.out.println("Balance: " + balances.balanceOf(account.accountNumber()).orElseThrow());

        // Behavioral – HOW objects talk to each other
        TransactionNotifier notifier = new TransactionNotifier();
        SmsAlert sms = new SmsAlert("+6591234567");
        notifier.subscribe(sms);
        notifier.publish(new Transaction(account.accountNumber(), new BigDecimal("80.00"), "NTUC FairPrice"));
        System.out.println(sms.sent());
    }
}
