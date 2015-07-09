package com.guigarage.material;

import javafx.animation.ParallelTransition;
import javafx.animation.Transition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.util.Duration;

import java.util.function.Consumer;

public class AnimatedIcon extends Region {

    private Line line1;

    private Line line2;

    private Line line3;

    private Duration animationDuration = Duration.millis(480);

    private ObjectProperty<Color> iconFill;

    private ParallelTransition transition;

    public AnimatedIcon() {
        iconFill = new SimpleObjectProperty<>(Color.BLACK);

        line1 = new Line(4, 8, 28, 8);
        line1.setStrokeWidth(3);
        line1.strokeProperty().bind(iconFill);
        line1.setManaged(false);
        line1.setStrokeLineCap(StrokeLineCap.ROUND);

        line2 = new Line(4, 16, 28, 16);
        line2.setStrokeWidth(3);
        line2.strokeProperty().bind(iconFill);
        line2.setManaged(false);
        line2.setStrokeLineCap(StrokeLineCap.ROUND);

        line3 = new Line(4, 24, 28, 24);
        line3.setStrokeWidth(3);
        line3.strokeProperty().bind(iconFill);
        line3.setManaged(false);
        line3.setStrokeLineCap(StrokeLineCap.ROUND);

        getChildren().addAll(line1, line2, line3);
        setPrefWidth(32);
        setPrefHeight(32);
        setMinWidth(32);
        setMinHeight(32);
        setMaxWidth(32);
        setMaxHeight(32);
    }

    public void toArrow() {
        stop();
        transition = new ParallelTransition();
        transition.getChildren().add(create(line1, 16, 6, 28, 16));
        transition.getChildren().add(create(line2, 4, 16, 24, 16));
        transition.getChildren().add(create(line3, 16, 26, 28, 16));

        transition.getChildren().add(createOpacityTransition(line1, 1));
        transition.getChildren().add(createOpacityTransition(line2, 1));
        transition.getChildren().add(createOpacityTransition(line3, 1));

        transition.getChildren().add(createRotationTransition(-180));
        transition.play();
    }

    public void toMenu() {
        stop();
        transition = new ParallelTransition();
        transition.getChildren().add(create(line1, 4, 8, 28, 8));
        transition.getChildren().add(create(line2, 4, 16, 28, 16));
        transition.getChildren().add(create(line3, 4, 24, 28, 24));

        transition.getChildren().add(createOpacityTransition(line1, 1));
        transition.getChildren().add(createOpacityTransition(line2, 1));
        transition.getChildren().add(createOpacityTransition(line3, 1));

        transition.getChildren().add(createRotationTransition(0));
        transition.play();
    }

    public void toBack() {
        stop();
        transition = new ParallelTransition();
        transition.getChildren().add(create(line1, 4, 16, 28, 4));
        transition.getChildren().add(create(line2, 4, 16, 28, 28));
        transition.getChildren().add(create(line3, 28, 4, 28, 28));

        transition.getChildren().add(createOpacityTransition(line1, 1));
        transition.getChildren().add(createOpacityTransition(line2, 1));
        transition.getChildren().add(createOpacityTransition(line3, 1));

        transition.getChildren().add(createRotationTransition(0));
        transition.play();
    }

    public void setLine(Line line, double startX, double startY, double endX, double endY, boolean opaque) {
        line.setStartX(startX);
        line.setStartY(startY);
        line.setEndX(endX);
        line.setEndY(endY);
        line.setOpacity(opaque ? 1 : 0);
    }

    public void jumpDirectlyToPlay() {
        stop();
        setLine(line1, 4, 16, 24, 4, true);
        setLine(line2, 4, 16, 24, 28, true);
        setLine(line3, 24, 4, 24, 28, true);
        setRotate(-180);
    }

    public void stop() {
        if(transition != null) {
            transition.stop();
        }
    }

    public void toPlay() {
        stop();
        transition = new ParallelTransition();
        transition.getChildren().add(create(line1, 4, 16, 24, 4));
        transition.getChildren().add(create(line2, 4, 16, 24, 28));
        transition.getChildren().add(create(line3, 24, 4, 24, 28));

        transition.getChildren().add(createOpacityTransition(line1, 1));
        transition.getChildren().add(createOpacityTransition(line2, 1));
        transition.getChildren().add(createOpacityTransition(line3, 1));

        transition.getChildren().add(createRotationTransition(-180));
        transition.play();
    }

    public void toClose() {
        stop();
        transition = new ParallelTransition();
        transition.getChildren().add(create(line1, 6, 6, 26, 26));
        transition.getChildren().add(create(line2, 4, 16, 28, 28));
        transition.getChildren().add(create(line3, 6, 26, 26, 6));

        transition.getChildren().add(createOpacityTransition(line1, 1));
        transition.getChildren().add(createOpacityTransition(line2, 0));
        transition.getChildren().add(createOpacityTransition(line3, 1));

        transition.getChildren().add(createRotationTransition(0));
        transition.play();
    }

    public void toPlus() {
        stop();
        transition = new ParallelTransition();
        transition.getChildren().add(create(line1, 16, 6, 16, 26));
        transition.getChildren().add(create(line2, 4, 16, 28, 28));
        transition.getChildren().add(create(line3, 6, 16, 26, 16));

        transition.getChildren().add(createOpacityTransition(line1, 1));
        transition.getChildren().add(createOpacityTransition(line2, 0));
        transition.getChildren().add(createOpacityTransition(line3, 1));

        transition.getChildren().add(createRotationTransition(0));
        transition.play();
    }

    public void toPause() {
        stop();
        transition = new ParallelTransition();
        transition.getChildren().add(create(line1, 12, 9, 12, 23));
        transition.getChildren().add(create(line2, 16, 9, 16, 23));
        transition.getChildren().add(create(line3, 20, 9, 20, 23));

        transition.getChildren().add(createOpacityTransition(line1, 1));
        transition.getChildren().add(createOpacityTransition(line2, 0));
        transition.getChildren().add(createOpacityTransition(line3, 1));

        transition.getChildren().add(createRotationTransition(0));
        transition.play();
    }

    private Transition createRotationTransition(double rotate) {
        double currentRotation = getRotate();
        return createAnimation(frac -> setRotate(currentRotation + (rotate - currentRotation) * frac));
    }

    private Transition createOpacityTransition(Line line, double opacity) {
        double currentOpacity = line.getOpacity();
        return createAnimation(frac -> line.setOpacity(currentOpacity + (opacity - currentOpacity) * frac));
    }

    private Transition create(Line line, double startX, double startY, double endX, double endY) {
        double currentStartX = line.getStartX();
        double currentStartY = line.getStartY();
        double currentEndX = line.getEndX();
        double currentEndY = line.getEndY();

        return createAnimation(frac -> {
            line.setStartX(currentStartX + (startX - currentStartX) * frac);
            line.setStartY(currentStartY + (startY - currentStartY) * frac);
            line.setEndX(currentEndX + (endX - currentEndX) * frac);
            line.setEndY(currentEndY + (endY - currentEndY) * frac);
        });
    }

    private Transition createAnimation(Consumer<Double> fractionConsumer) {
        Transition transition = new Transition() {

            {
                setCycleDuration(animationDuration);
            }

            @Override
            protected void interpolate(double frac) {
                fractionConsumer.accept(frac);


            }
        };
        return transition;
    }

    public Color getIconFill() {
        return iconFill.get();
    }

    public void setIconFill(Color iconFill) {
        this.iconFill.set(iconFill);
    }

    public ObjectProperty<Color> iconFillProperty() {
        return iconFill;
    }
}
