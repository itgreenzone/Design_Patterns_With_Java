package com.itgreenzone.patterns.behavioral.interpreter.after;

import com.itgreenzone.patterns.behavioral.interpreter.ApplicantContext;

/** Underwriters edit the rule TEXT; no Java release needed. */
public class AfterDemo {

    public static void main(String[] args) {
        String ruleText = "age >= 18 AND age <= 65 AND (country == SG OR country == MY)";
        Rule rule = new RuleParser().parse(ruleText);
        System.out.println(rule);
        System.out.println("Alice, 42, SG: " + rule.interpret(ApplicantContext.of(42, "SG", false)));
        System.out.println("Ken, 70, MY:   " + rule.interpret(ApplicantContext.of(70, "MY", false)));
        System.out.println("Ana, 30, TH:   " + rule.interpret(ApplicantContext.of(30, "TH", false)));
    }
}
