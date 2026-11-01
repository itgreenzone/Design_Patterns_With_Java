package com.itgreenzone.patterns.creational.builder.after;

import java.math.BigDecimal;

/** Readable, validated, immutable – and the Director knows the standard recipes. */
public class AfterDemo {

    public static void main(String[] args) {
        LoanApplication custom = LoanApplication.builder("Alice Tan", new BigDecimal("80000.00"), 60)
                .coApplicant("Ben Tan")
                .employer("ITGreenZone")
                .disbursementAccount("DBS-001-234567")
                .build();
        System.out.println(custom.applicant() + " + " + custom.coApplicant().orElse("-") + ", " + custom.tenureMonths() + " months");

        LoanApplication home = new LoanApplicationDirector().homeLoan("Raj Kumar", new BigDecimal("900000.00"), new BigDecimal("1200000.00"));
        System.out.println("Home loan with insurance? " + home.insuranceAddOn());
    }
}
