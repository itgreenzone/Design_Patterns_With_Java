package com.itgreenzone.patterns.foundations.intro.solution;

import com.itgreenzone.patterns.foundations.intro.exercise.DesignProblem;
import com.itgreenzone.patterns.foundations.intro.exercise.PatternCategory;

import static com.itgreenzone.patterns.foundations.intro.exercise.PatternCategory.*;

/**
 * Reference answers, with the pattern each problem points to.
 */
public class CategoryAnswers {

    public PatternCategory classify(DesignProblem problem) {
        return switch (problem.id()) {
            case 1 -> CREATIONAL;   // Singleton
            case 2 -> BEHAVIORAL;   // Command (with undo)
            case 3 -> STRUCTURAL;   // Adapter
            case 4 -> CREATIONAL;   // Abstract Factory
            case 5 -> STRUCTURAL;   // Decorator
            case 6 -> BEHAVIORAL;   // Observer
            case 7 -> STRUCTURAL;   // Composite
            case 8 -> BEHAVIORAL;   // Chain of Responsibility
            default -> throw new IllegalArgumentException("Unknown problem " + problem.id());
        };
    }

    public String suggestedPattern(int id) {
        return switch (id) {
            case 1 -> "Singleton";
            case 2 -> "Command";
            case 3 -> "Adapter";
            case 4 -> "Abstract Factory";
            case 5 -> "Decorator";
            case 6 -> "Observer";
            case 7 -> "Composite";
            case 8 -> "Chain of Responsibility";
            default -> throw new IllegalArgumentException("Unknown problem " + id);
        };
    }
}
