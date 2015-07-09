package com.guigarage.extreme;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class LoadingAnimationPane extends Region {

    private Circle redCircle;
    private Circle blueCircle;
    private Circle yellowCircle;
    private Circle greenCircle;

    private double radius;

    private double spacing;

    public LoadingAnimationPane(double radius) {
        this.radius = radius;
        spacing = radius;
        redCircle = new Circle(radius, Color.RED);
        blueCircle = new Circle(radius, Color.BLUE);
        yellowCircle = new Circle(radius, Color.YELLOW);
        greenCircle = new Circle(radius, Color.GREEN);
        getChildren().addAll(redCircle, blueCircle, yellowCircle, greenCircle);

        setMaxHeight(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);

        TranslateTransition redTransition = new TranslateTransition(Duration.millis(800), redCircle);
        redTransition.setFromY(-radius);
        redTransition.setToY(radius);
        redTransition.setAutoReverse(true);
        redTransition.setCycleCount(TranslateTransition.INDEFINITE);

        TranslateTransition blueTransition = new TranslateTransition(Duration.millis(800), blueCircle);
        blueTransition.setFromY(-radius);
        blueTransition.setToY(radius);
        blueTransition.setDelay(Duration.millis(200));
        blueTransition.setAutoReverse(true);
        blueTransition.setCycleCount(TranslateTransition.INDEFINITE);

        TranslateTransition yellowTransition = new TranslateTransition(Duration.millis(800), yellowCircle);
        yellowTransition.setFromY(-radius);
        yellowTransition.setToY(radius);
        yellowTransition.setDelay(Duration.millis(400));
        yellowTransition.setAutoReverse(true);
        yellowTransition.setCycleCount(TranslateTransition.INDEFINITE);

        TranslateTransition greenTransition = new TranslateTransition(Duration.millis(800), greenCircle);
        greenTransition.setFromY(-radius);
        greenTransition.setToY(radius);
        greenTransition.setDelay(Duration.millis(600));
        greenTransition.setAutoReverse(true);
        greenTransition.setCycleCount(TranslateTransition.INDEFINITE);

        sceneProperty().addListener(e -> {
            if(getScene() == null) {
                redTransition.stop();
                blueTransition.stop();
                yellowTransition.stop();
                greenTransition.stop();
            } else {
                redTransition.playFromStart();
                blueTransition.playFromStart();
                yellowTransition.playFromStart();
                greenTransition.playFromStart();
            }
        });
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        redCircle.relocate(0, radius);
        blueCircle.relocate(radius * 3, radius);
        yellowCircle.relocate(radius * 6, radius);
        greenCircle.relocate(radius * 9, radius);

    }

    @Override
    protected double computePrefWidth(double height) {
        return getInsets().getLeft() + radius * 11 + getInsets().getRight();
    }

    @Override
    protected double computePrefHeight(double width) {
        return getInsets().getTop() + radius * 6 + getInsets().getBottom();
    }
}
