package com.guigarage.demos.bubbles;

import com.guigarage.incubator.bubbles.LabTube;
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

public class LoadingAnimation extends Application {

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        LabTube tube = new LabTube();
        tube.setScaleX(0.5);
        tube.setScaleY(0.5);

        Arc backgroundCircle = new Arc(260, 300, 204, 204, 0, 360);
        backgroundCircle.setStroke(Color.BLACK);
        backgroundCircle.setStrokeWidth(8);
        backgroundCircle.setFill(Color.LIGHTGRAY);

        Arc loadingCircle = new Arc(260, 300, 200, 200, 0, 0);
        loadingCircle.setType(ArcType.ROUND);
        loadingCircle.setFill(Color.web("#ff8087").brighter().brighter());

        Group loadingGroup = new Group();
        loadingGroup.getChildren().add(backgroundCircle);
        loadingGroup.getChildren().add(loadingCircle);
        loadingGroup.getChildren().add(tube);

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

        Scene scene = new Scene(new StackPane(loadingGroup));
        scene.setFill(Color.WHITESMOKE);
        primaryStage.setScene(scene);
        primaryStage.setOnHidden(e -> System.exit(0));
        primaryStage.show();
    }
}
