package com.itgreenzone.patterns.behavioral.interpreter;

import com.itgreenzone.patterns.behavioral.interpreter.after.*;
import com.itgreenzone.patterns.behavioral.interpreter.before.EligibilityService;
import com.itgreenzone.patterns.behavioral.interpreter.modern.Expr;
import com.itgreenzone.patterns.behavioral.interpreter.solution.InExpression;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InterpreterTest {

    private static final String RULE = "age >= 18 AND age <= 65 AND (country == SG OR country == MY)";

    @Test
    void treeBuiltByHand() {
        Rule rule = new And(
                new And(new Comparison(new Variable("age"), ">=", new Constant(18)),
                        new Comparison(new Variable("age"), "<=", new Constant(65))),
                new Or(new Comparison(new Variable("country"), "==", new Constant("SG")),
                       new Comparison(new Variable("country"), "==", new Constant("MY"))));
        assertTrue(rule.interpret(ApplicantContext.of(42, "SG", false)));
        assertFalse(rule.interpret(ApplicantContext.of(42, "TH", false)));
        assertEquals(rule, new RuleParser().parse(RULE), "the parser builds exactly the same tree");
    }

    @ParameterizedTest
    @CsvSource({"17, SG, false", "18, SG, true", "65, MY, true", "66, MY, false", "30, TH, false"})
    void parsedRuleMatchesTheHardCodedRule(int age, String country, boolean expected) {
        ApplicantContext c = ApplicantContext.of(age, country, false);
        assertEquals(expected, new RuleParser().parse(RULE).interpret(c));
        assertEquals(expected, new EligibilityService().isEligible(c));
    }

    @Test
    void underwritersChangeTheRuleAsText() {
        Rule newRule = new RuleParser().parse("age >= 21 AND age <= 70 AND NOT smoker == 1");
        assertTrue(newRule.interpret(ApplicantContext.of(70, "TH", false)));
        assertFalse(newRule.interpret(ApplicantContext.of(40, "SG", true)));
    }

    @Test
    void andBindsTighterThanOr() {
        Rule r = new RuleParser().parse("country == TH OR age >= 18 AND age <= 20");
        assertTrue(r.interpret(ApplicantContext.of(90, "TH", false)), "parsed as TH OR (18..20)");
        assertFalse(r.interpret(ApplicantContext.of(90, "SG", false)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"age >=", "age >= 18 AND", "(age >= 18", "age ~ 18", "age >= 18)"})
    void badRulesAreRejectedWithAMessage(String bad) {
        assertThrows(IllegalArgumentException.class, () -> new RuleParser().parse(bad));
    }

    @Test
    void sealedRecordsWithPatternMatching() {
        Expr e = new Expr.And(new Expr.Ge(new Expr.Var("age"), new Expr.Num(18)),
                              new Expr.Or(new Expr.Eq(new Expr.Var("country"), new Expr.Text("SG")),
                                          new Expr.Eq(new Expr.Var("country"), new Expr.Text("MY"))));
        assertEquals(true, Expr.eval(e, ApplicantContext.of(30, "MY", false)));
        assertEquals(false, Expr.eval(new Expr.Not(e), ApplicantContext.of(30, "MY", false)));
    }

    @Test
    void inOperatorSolution() {
        Rule in = new InExpression(new Variable("country"), List.of(new Constant("SG"), new Constant("MY"), new Constant("TH")));
        assertTrue(in.interpret(ApplicantContext.of(30, "TH", false)));
        assertFalse(in.interpret(ApplicantContext.of(30, "ID", false)));
        Rule combined = new And(new Comparison(new Variable("age"), ">=", new Constant(18)), in);
        assertFalse(combined.interpret(ApplicantContext.of(16, "SG", false)));
    }
}
