package com.itgreenzone.patterns.creational.builder;

import com.itgreenzone.patterns.creational.builder.after.LoanApplication;
import com.itgreenzone.patterns.creational.builder.after.LoanApplicationDirector;
import com.itgreenzone.patterns.creational.builder.before.LoanApplicationBean;
import com.itgreenzone.patterns.creational.builder.modern.LoanRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BuilderTest {

    private static final BigDecimal SMALL = new BigDecimal("80000.00");
    private static final BigDecimal LARGE = new BigDecimal("900000.00");

    @Test
    void beanCanBeSubmittedHalfBuilt() {
        LoanApplicationBean bean = new LoanApplicationBean();
        bean.setApplicant("Alice");
        assertNull(bean.getAmount(), "nothing stopped us using an incomplete object");
    }

    @Test
    void builderSetsOptionalFieldsReadably() {
        LoanApplication app = LoanApplication.builder("Alice", SMALL, 60)
                .coApplicant("Ben").employer("ITGreenZone").withInsurance().build();
        assertEquals("Ben", app.coApplicant().orElseThrow());
        assertTrue(app.insuranceAddOn());
        assertTrue(app.promoCode().isEmpty());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 11, 361})
    void tenureOutsideRangeIsRejected(int months) {
        assertThrows(IllegalStateException.class, () -> LoanApplication.builder("A", SMALL, months).build());
    }

    @Test
    void largeLoanNeedsCollateral() {
        assertThrows(IllegalStateException.class, () -> LoanApplication.builder("A", LARGE, 300).build());
        assertDoesNotThrow(() -> LoanApplication.builder("A", LARGE, 300).collateral(new BigDecimal("1200000.00")).build());
    }

    @Test
    void requiredFieldsCannotBeNull() {
        assertThrows(NullPointerException.class, () -> LoanApplication.builder(null, SMALL, 60));
    }

    @Test
    void directorKnowsTheStandardRecipes() {
        LoanApplicationDirector director = new LoanApplicationDirector();
        LoanApplication home = director.homeLoan("Raj", LARGE, new BigDecimal("1200000.00"));
        assertEquals(300, home.tenureMonths());
        assertTrue(home.insuranceAddOn());
        LoanApplication car = director.carLoan("Mei", new BigDecimal("60000.00"));
        assertEquals(84, car.tenureMonths());
        assertEquals("DRIVE2026", car.promoCode().orElseThrow());
    }

    @Test
    void recordBuilderValidatesInCompactConstructor() {
        LoanRequest r = LoanRequest.builder("Alice", SMALL, 60).coApplicant("Ben").build();
        assertEquals("Ben", r.coApplicant().orElseThrow());
        assertEquals(r, LoanRequest.builder("Alice", SMALL, 60).coApplicant("Ben").build());   // records: value equality
        assertThrows(IllegalArgumentException.class, () -> LoanRequest.builder("A", LARGE, 300).build());
    }
}
