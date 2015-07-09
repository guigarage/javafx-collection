package com.guigarage.incubator.bubbles;

import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;

public class LabTube extends Region {

    private BubblePane bubblePane;

    public LabTube() {
        getStylesheets().add(LabTube.class.getResource("skin.css").toExternalForm());
        Shape tubeClip = createTube();
        tubeClip.setStroke(Color.BLACK);
        tubeClip.setStrokeWidth(10);
        tubeClip.setFill(Color.BLACK);
        setClip(tubeClip);
        setMaxWidth(tubeClip.getLayoutBounds().getMaxX());
        setMaxHeight(tubeClip.getLayoutBounds().getMaxY());
        setMinWidth(tubeClip.getLayoutBounds().getMaxX());
        setMinHeight(tubeClip.getLayoutBounds().getMaxY());
        setPrefWidth(tubeClip.getLayoutBounds().getMaxX());
        setPrefHeight(tubeClip.getLayoutBounds().getMaxY());

        Rectangle tubeBackground = new Rectangle();
        tubeBackground.widthProperty().bind(widthProperty());
        tubeBackground.heightProperty().bind(heightProperty());
        tubeBackground.getStyleClass().add("tube-background");

        bubblePane = new BubblePane();

        Shape tubeBorder = createTube();
        tubeBorder.setStroke(Color.BLACK);
        tubeBorder.setStrokeWidth(10);
        tubeBorder.setFill(Color.TRANSPARENT);

        getChildren().add(tubeBackground);
        getChildren().add(bubblePane);
        getChildren().add(tubeBorder);



        Circle reflection = new Circle(400, 360, 180);
        reflection.setFill(Color.BLACK);
        reflection.setOpacity(0.2);
        reflection.setScaleX(0.1);
        reflection.setRotate(-30);

        getChildren().add(reflection);

    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        bubblePane.resize(getWidth(), getHeight() / 2);
        bubblePane.relocate(0, getHeight() / 2);
    }

    public Shape createTube() {
        SVGPath svg = new SVGPath();
        svg.setContent("M513.246462,472.73363 C513.246462,472.73363 445.165615,339.840519 334.120154,148.551148 L334.120154,37.7225926 L340.986462,37.7225926 C350.884385,37.7225926 358.955308,29.292963 358.955308,18.9034444 C358.955308,8.49285185 350.904462,0 340.986462,0 L178.483846,0 C168.565846,0 160.474846,8.49285185 160.474846,18.9034444 C160.474846,29.292963 168.545769,37.7225926 178.463769,37.7225926 L185.330077,37.7225926 L185.330077,148.530074 C74.3046923,339.861593 6.16361538,473.155111 6.16361538,473.155111 C-11.1226154,527.104741 7.91030769,576.649889 63.2423077,576.649889 L456.207923,576.649889 C511.519846,576.649889 530.592923,526.683259 513.246462,472.73363 L513.246462,472.73363 Z");
        return svg;
    }


}
