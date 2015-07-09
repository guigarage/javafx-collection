package com.guigarage.animations.transitions;

import javafx.animation.Transition;
import javafx.util.Duration;

public abstract class DurationBasedTransition extends Transition {

    public DurationBasedTransition(Duration duration) {
        setCycleDuration(duration);
    }

}
