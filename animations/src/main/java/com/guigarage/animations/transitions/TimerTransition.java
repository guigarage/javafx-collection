package com.guigarage.animations.transitions;

import com.guigarage.animations.transitions.DurationBasedTransition;
import javafx.util.Duration;

/**
 * Created with IntelliJ IDEA.
 * User: hendrikebbers
 * Date: 04.07.14
 * Time: 12:39
 * To change this template use File | Settings | File Templates.
 */
public class TimerTransition extends DurationBasedTransition {

    public TimerTransition(Duration duration) {
        super(duration);
    }

    @Override
    protected void interpolate(double frac) {

    }
}
