package com.guigarage.demos.bubbles;

import com.guigarage.incubator.bubbles.BubblePane;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Created with IntelliJ IDEA.
 * User: hendrikebbers
 * Date: 16.08.14
 * Time: 16:57
 * To change this template use File | Settings | File Templates.
 */
public class LoadingAnimation2  extends Application {

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Arc backgroundCircle = new Arc(200, 200, 203, 203, 0, 360);
        backgroundCircle.setStroke(Color.BLACK);
        backgroundCircle.setStrokeWidth(6);
        backgroundCircle.setFill(Color.LIGHTGRAY);

        Arc loadingCircle = new Arc(200, 200, 200, 200, 0, 0);
        loadingCircle.setType(ArcType.ROUND);
        loadingCircle.setFill(Color.web("#ff8087").brighter().brighter());

        BubblePane bubblePane = new BubblePane() {

            @Override
            protected double calcBubbleRadius() {
                return super.calcBubbleRadius() / 1.5;
            }

            @Override
            protected int getMaxBubbleCount() {
                return 50;
            }
        };
        bubblePane.setStyle("-fx-border-color: transparent");
        bubblePane.setPrefWidth(400);
        bubblePane.setPrefHeight(400);
        bubblePane.setClip(loadingCircle);

        Group loadingGroup = new Group();
        loadingGroup.getChildren().add(backgroundCircle);
        loadingGroup.getChildren().add(bubblePane);

        Transition loadingTransition = new Transition() {

            {
                setCycleDuration(Duration.seconds(12));
            }

            @Override
            protected void interpolate(double frac) {
                loadingCircle.setLength(360.0 * frac);
            }
        };
        loadingTransition.play();

        Transition pulseTransition = new Transition() {

            {
                setCycleDuration(Duration.millis(1024));
            }

            @Override
            protected void interpolate(double frac) {
                backgroundCircle.setStrokeWidth(6.0 + 12.0 * frac);
                backgroundCircle.setScaleY(1.0 + frac * 0.05);
                backgroundCircle.setScaleX(1.0 + frac * 0.05);
                backgroundCircle.setOpacity(1.0 - 0.5 * frac);
            }
        };
        pulseTransition.setAutoReverse(true);
        pulseTransition.setCycleCount(Animation.INDEFINITE);
        pulseTransition.play();


        Scene scene = new Scene(new StackPane(loadingGroup));
        scene.setFill(Color.WHITESMOKE);
        primaryStage.setScene(scene);
        primaryStage.setOnHidden(e -> System.exit(0));
        primaryStage.show();
    }
}
