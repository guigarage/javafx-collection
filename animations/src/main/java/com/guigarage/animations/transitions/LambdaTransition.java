package com.guigarage.animations.transitions;

import javafx.util.Duration;

public class LambdaTransition extends DurationBasedTransition {

    private FunctionalTransition transition;

    public LambdaTransition(Duration duration, FunctionalTransition transition) {
        super(duration);
        this.transition = transition;
    }

    @Override
    protected void interpolate(double frac) {
        transition.interpolate(frac);
    }
}
