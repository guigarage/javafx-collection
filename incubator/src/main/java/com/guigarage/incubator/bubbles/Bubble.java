package com.guigarage.incubator.bubbles;

import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;

public class Bubble extends Region {

    private double radius;

    public Bubble(double radius) {
        getStylesheets().add(Bubble.class.getResource("skin.css").toExternalForm());
        Circle bubble = new Circle(radius, radius, radius);
        bubble.getStyleClass().add("bubble");
        bubble.setStrokeWidth(radius / 9 + (Math.random() - 0.5));

        Circle reflection = new Circle(radius * 2 - radius / 2, radius / 2, radius / 3 + (radius / 20) * Math.random());
        reflection.fillProperty().bind(bubble.strokeProperty());
        reflection.setOpacity(0.6);
        reflection.setScaleX(0.4 + 0.1 * Math.random());
        reflection.setRotate(-40 + 6.0 * (Math.random() - 0.5));

        getChildren().add(bubble);
        getChildren().add(reflection);
    }

    public double getRadius() {
        return radius;
    }
}
