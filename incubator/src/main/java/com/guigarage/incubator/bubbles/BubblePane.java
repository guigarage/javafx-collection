package com.guigarage.incubator.bubbles;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.util.Duration;

import java.util.concurrent.Executors;

public class BubblePane extends Region {

    public BubblePane() {
        getStylesheets().add(BubblePane.class.getResource("skin.css").toExternalForm());
        getStyleClass().add("bubble-pane");
        Executors.newSingleThreadExecutor().execute(() -> {
            while (true) {
                if (getChildren().size() < getMaxBubbleCount()) {
                    Platform.runLater(() -> {
                        getChildren().add(createBubble());
                    });
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected int getMaxBubbleCount() {
         return 7;
    }

    protected double calcBubbleRadius() {
        return getWidth() / 16 + (Math.random() - 0.5) * getWidth() / 24;
    }

    private Node createBubble() {
        Bubble bubble = new Bubble(calcBubbleRadius());

        double defaultOpaque = 0.8 - Math.random() / 4.0;

        bubble.setOpacity(defaultOpaque);
        bubble.setTranslateX(Math.random() * getWidth());


        Duration translateDuration = Duration.seconds(2 + Math.random() * 2);
        TranslateTransition transition = new TranslateTransition(translateDuration, bubble);
        transition.setFromY(getHeight() - bubble.getRadius() * 2);
        transition.setToY(-bubble.getRadius());
        transition.setInterpolator(Interpolator.EASE_IN);
        Duration fadeOutTranslationDuration = Duration.millis(100 + Math.random() * 200);
        Transition fadeOutTranslation = new Transition() {

            {
                setCycleDuration(fadeOutTranslationDuration);
            }

            @Override
            protected void interpolate(double frac) {
                bubble.setOpacity(defaultOpaque - frac);
            }
        };
        fadeOutTranslation.setDelay(translateDuration.subtract(fadeOutTranslationDuration));

        ScaleTransition fadeOutSpringTranslation = new ScaleTransition(fadeOutTranslationDuration, bubble);
        fadeOutSpringTranslation.setFromX(bubble.getScaleX());
        fadeOutSpringTranslation.setFromY(bubble.getScaleY());
        fadeOutSpringTranslation.setToX(bubble.getScaleX() * 1.5);
        fadeOutSpringTranslation.setToY(bubble.getScaleY() * 1.5);
        fadeOutSpringTranslation.setDelay(translateDuration.subtract(fadeOutTranslationDuration));

        fadeOutTranslation.setOnFinished(e -> {
            getChildren().remove(bubble);

        });

        Transition fadeInTranslation = new Transition() {

            {
                setCycleDuration(Duration.seconds(1 + Math.random() * 2));
            }

            @Override
            protected void interpolate(double frac) {
                bubble.setOpacity(frac * defaultOpaque);
            }
        };

        TranslateTransition shakeTranslation = new TranslateTransition(Duration.millis(600 + Math.random() * 100), bubble);
        shakeTranslation.setFromX(bubble.getTranslateX());
        shakeTranslation.setToX(bubble.getTranslateX() + Math.random() * 8);
        shakeTranslation.setCycleCount(Animation.INDEFINITE);
        shakeTranslation.setAutoReverse(true);


        ScaleTransition deformTransition = new ScaleTransition(Duration.millis(800 + Math.random() * 100), bubble);
        deformTransition.setFromX(1.0);
        deformTransition.setFromY(1.0);
        deformTransition.setToX(1.0 + (Math.random() * 0.1));
        deformTransition.setToY(1.0 + (Math.random() * 0.1));
        deformTransition.setCycleCount(Animation.INDEFINITE);
        deformTransition.setAutoReverse(true);

        transition.play();
        deformTransition.play();
        shakeTranslation.play();
        fadeInTranslation.play();
        fadeOutTranslation.play();
        fadeOutSpringTranslation.play();

        return bubble;
    }

}

