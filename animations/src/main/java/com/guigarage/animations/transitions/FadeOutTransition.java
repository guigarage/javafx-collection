package com.guigarage.animations.transitions;

import com.guigarage.animations.transitions.DurationBasedTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class FadeOutTransition extends DurationBasedTransition {

    private Node node;

    public FadeOutTransition(Duration duration, Node node) {
        super(duration);
        this.node = node;
    }

    @Override
    protected void interpolate(double frac) {
        node.setOpacity(1.0 - frac);
    }
}
