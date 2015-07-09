package com.guigarage.animations.transitions;

import com.guigarage.animations.transitions.DurationBasedTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class KenBurnsTransition extends DurationBasedTransition {

    private ImageView node;

    public KenBurnsTransition(ImageView node, Duration duration) {
        super(duration);
        this.node = node;
        statusProperty().addListener(e -> {
            if(getStatus().equals(Status.RUNNING)) {
                node.setTranslateX(50);
                node.setScaleX(1.2);
                node.setScaleY(1.2);
            }
        });
    }

    @Override
    protected void interpolate(double frac) {
        node.setTranslateX(50 - (frac * 100.0));
        node.setScaleX(1.2 + 0.4 * frac);
        node.setScaleY(1.2 + 0.4 * frac);
    }
}
