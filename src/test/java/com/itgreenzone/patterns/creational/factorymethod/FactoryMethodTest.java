package com.itgreenzone.patterns.creational.factorymethod;

import com.itgreenzone.patterns.creational.factorymethod.after.*;
import com.itgreenzone.patterns.creational.factorymethod.before.PolicyService;
import com.itgreenzone.patterns.creational.factorymethod.before.ProductType;
import com.itgreenzone.patterns.creational.factorymethod.modern.PolicyFactory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class FactoryMethodTest {

    private final QuoteRequest req = new QuoteRequest("Alice Tan", 42, new BigDecimal("80000.00"), 10);

    @Test
    void eachConcreteCreatorProducesItsOwnPolicy() {
        assertInstanceOf(MotorPolicy.class, new MotorPolicyIssuer().issue(req).policy());
        assertInstanceOf(TravelPolicy.class, new TravelPolicyIssuer().issue(req).policy());
        assertInstanceOf(HealthPolicy.class, new HealthPolicyIssuer().issue(req).policy());
    }

    @Test
    void premiumsFollowEachProductsRules() {
        assertEquals(new BigDecimal("2400.00"), new MotorPolicyIssuer().issue(req).policy().annualPremium());
        assertEquals(new BigDecimal("50.00"), new TravelPolicyIssuer().issue(req).policy().annualPremium());
        assertEquals(new BigDecimal("780.00"), new HealthPolicyIssuer().issue(req).policy().annualPremium());
    }

    @Test
    void sharedWorkflowNumbersAndWelcomesEveryPolicy() {
        IssuedPolicy p = new HealthPolicyIssuer().issue(req);
        assertTrue(p.policyNumber().startsWith("HEALTH-"));
        assertTrue(p.welcomeLetter().contains("Alice Tan"));
    }

    @Test
    void sharedWorkflowRejectsMinorsForEveryProduct() {
        QuoteRequest minor = new QuoteRequest("Tim", 16, new BigDecimal("1000.00"), 3);
        assertThrows(IllegalArgumentException.class, () -> new MotorPolicyIssuer().issue(minor));
        assertThrows(IllegalArgumentException.class, () -> new HomePolicyIssuer().issue(minor));
    }

    @Test
    void homePolicyAddedWithoutChangingExistingCode() {
        IssuedPolicy p = new HomePolicyIssuer().issue(req);
        assertEquals("HOME", p.policy().product());
        assertEquals(new BigDecimal("160.00"), p.policy().annualPremium());
    }

    @Test
    void creatorSpecificRuleStillApplies() {
        QuoteRequest noTrip = new QuoteRequest("Bob", 30, BigDecimal.ONE, 0);
        assertThrows(IllegalArgumentException.class, () -> new TravelPolicyIssuer().issue(noTrip));
    }

    @Test
    void beforeAndAfterAgreeOnPremiums() {
        PolicyService before = new PolicyService();
        assertEquals(new MotorPolicyIssuer().issue(req).policy().annualPremium(),
                before.issue(ProductType.MOTOR, req).policy().annualPremium());
    }

    @Test
    void modernRegistryCreatesByCodeAndAcceptsNewLambdas() {
        PolicyFactory factory = PolicyFactory.withDefaults();
        assertEquals("TRAVEL", factory.create("TRAVEL", req).product());
        factory.register("HOME", r -> new HomePolicy(r.sumInsured()));
        assertEquals("HOME", factory.create("HOME", req).product());
        assertThrows(IllegalArgumentException.class, () -> factory.create("PET", req));
    }
}
