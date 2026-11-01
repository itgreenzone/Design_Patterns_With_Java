package com.itgreenzone.patterns.structural.adapter.vendor;

import java.util.Map;

/** A SECOND vendor: a modern API with its own shape (300–900 scale, record response). */
public class ModernScoreApi {

    public record ScoreResponse(String subject, int value, boolean found) {
    }

    private final Map<String, Integer> scores;

    public ModernScoreApi(Map<String, Integer> scores) {
        this.scores = Map.copyOf(scores);
    }

    public ScoreResponse lookup(String subject) {
        Integer v = scores.get(subject);
        return new ScoreResponse(subject, v == null ? 0 : v, v != null);
    }
}
