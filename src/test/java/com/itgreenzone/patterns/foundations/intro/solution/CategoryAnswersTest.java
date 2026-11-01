package com.itgreenzone.patterns.foundations.intro.solution;

import com.itgreenzone.patterns.foundations.intro.exercise.DesignProblem;
import com.itgreenzone.patterns.foundations.intro.exercise.PatternCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CategoryAnswersTest {

    private final CategoryAnswers answers = new CategoryAnswers();

    @ParameterizedTest
    @CsvSource({"1, CREATIONAL", "2, BEHAVIORAL", "3, STRUCTURAL", "4, CREATIONAL",
                "5, STRUCTURAL", "6, BEHAVIORAL", "7, STRUCTURAL", "8, BEHAVIORAL"})
    void classifiesEachProblem(int id, PatternCategory expected) {
        DesignProblem problem = DesignProblem.ALL.get(id - 1);
        assertEquals(expected, answers.classify(problem));
    }

    @Test
    void everyProblemHasAnAnswerAndAPattern() {
        DesignProblem.ALL.forEach(p -> {
            assertNotNull(answers.classify(p));
            assertFalse(answers.suggestedPattern(p.id()).isBlank());
        });
    }
}
