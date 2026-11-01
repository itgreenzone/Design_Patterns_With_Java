package com.itgreenzone.patterns.structural.adapter.modern;

import com.itgreenzone.patterns.structural.adapter.CreditCheckUnavailableException;
import com.itgreenzone.patterns.structural.adapter.CreditScore;
import com.itgreenzone.patterns.structural.adapter.after.CreditScoreProvider;

import java.util.OptionalInt;
import java.util.function.Function;

/**
 * MODERN VARIANT: because the Target has ONE method, an adapter can be a lambda.
 * fromScale() adapts ANY "id -> optional raw score" function on any scale.
 */
public final class ScoreAdapters {

    private ScoreAdapters() {
    }

    public static CreditScoreProvider fromScale(Function<String, OptionalInt> source, int min, int max) {
        return id -> {
            int raw = source.apply(id).orElseThrow(() -> new CreditCheckUnavailableException("No score for " + id, null));
            return new CreditScore(300 + (raw - min) * 550 / (max - min));
        };
    }
}
