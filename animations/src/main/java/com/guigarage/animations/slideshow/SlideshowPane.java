package com.guigarage.animations.slideshow;

import com.guigarage.animations.transitions.DurationBasedTransition;
import com.guigarage.ui.UiUtilities;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class SlideshowPane extends StackPane {

    private Text playIcon;

    private Text pauseIcon;

    private DurationBasedTransition iconTransition;

    public SlideshowPane(Slideshow slideshow) {
        playIcon = UiUtilities.createIconText('\uf04b', 128);
        playIcon.setFill(Color.WHITE);
        pauseIcon = UiUtilities.createIconText('\uf04c', 128);
        pauseIcon.setFill(Color.WHITE);

        setOnMouseClicked(e -> {
            getChildren().remove(playIcon);
            getChildren().remove(pauseIcon);

            if(slideshow.isPlaying()) {
                show(pauseIcon);
                slideshow.pause();
            } else {
                show(playIcon);
                slideshow.play();
            }
        });
    }

    private void show(Text icon) {

        icon.setOpacity(1);
        icon.setScaleX(1);
        icon.setScaleY(1);

        getChildren().add(icon);
        icon.toFront();
        if(iconTransition != null) {
            iconTransition.stop();
        }
        iconTransition = new DurationBasedTransition(Duration.millis(320)) {
            @Override
            protected void interpolate(double frac) {
                icon.setOpacity(1.0 - frac);
                icon.setScaleX(1.0 + frac);
                icon.setScaleY(1.0 + frac);
            }
        };
        iconTransition.play();
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        for(Node child : getChildren()) {
            if(!child.equals(playIcon) && !child.equals(pauseIcon)) {
                child.relocate(0, 0);
                child.resize(getWidth(), getHeight());
            }
        }
    }
}
