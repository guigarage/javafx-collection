package com.guigarage.demos.material;

import com.guigarage.material.AnimatedIcon;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AnimatedToolbarDemo extends Application {

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FlowPane toolbar = new FlowPane();
        toolbar.setId("toolbar");
        toolbar.setHgap(4);
        Label actionLabel = new Label("Menu");
        actionLabel.setId("toolbar-action");
        actionLabel.setManaged(false);
        actionLabel.resize(200, 40);
        actionLabel.relocate(40, 4);

        Label actionLabel2 = new Label("Settings");
        actionLabel2.setId("toolbar-action");
        actionLabel2.setManaged(false);
        actionLabel2.resize(200, 40);
        actionLabel2.relocate(40, 4);
        actionLabel2.setOpacity(0);

        AnimatedIcon icon = new AnimatedIcon();
        toolbar.getChildren().addAll(icon, actionLabel, actionLabel2);

        Rectangle toolbarClip = new Rectangle();
        toolbarClip.widthProperty().bind(toolbar.widthProperty());
        toolbarClip.heightProperty().bind(toolbar.heightProperty());
        toolbar.setClip(toolbarClip);


        Circle circle = new Circle();
        circle.centerXProperty().bind(icon.layoutXProperty().add(icon.widthProperty().divide(2)));
        circle.centerYProperty().bind(icon.layoutYProperty().add(icon.heightProperty().divide(2)));
        circle.setRadius(0);
        circle.setManaged(false);
        circle.setFill(Color.LIGHTBLUE);
        toolbar.getChildren().add(circle);
        circle.toBack();

        icon.setOnMouseClicked(e -> {

            double v = Math.random();
            if (v < 0.2) {
                icon.toBack();
            } else if (v < 0.4) {
                icon.toPlay();
            } else if (v < 0.6) {
                icon.toArrow();
            } else if (v < 0.8) {
                icon.toClose();
            } else {
                icon.toMenu();
            }


            Transition circleTransition = new Transition() {

                {
                    setCycleDuration(Duration.millis(480));
                }

                @Override
                protected void interpolate(double frac) {
                    circle.setRadius((toolbar.getWidth() + 32) * frac);
                    circle.setOpacity(0.2 + frac);

                    actionLabel.setOpacity(1 - frac * 1.5);
                    actionLabel.setTranslateY(-actionLabel.getLayoutBounds().getMaxY() * frac);

                    actionLabel2.setOpacity(frac);
                    actionLabel2.setTranslateY(actionLabel.getLayoutBounds().getMaxY() - actionLabel.getLayoutBounds().getMaxY() * frac);
                }
            };
            circleTransition.play();
        });

        Label content = new Label("Content");
        StackPane contentPane = new StackPane(content);
        contentPane.setStyle("-fx-background-color: white");

        VBox pane = new VBox();
        pane.getChildren().addAll(toolbar, contentPane);
        VBox.setVgrow(contentPane, Priority.ALWAYS);

        Scene scene = new Scene(pane);
        scene.getStylesheets().add(AnimatedToolbarDemo.class.getResource("demo-skin.css").toExternalForm());
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        primaryStage.setScene(scene);
        primaryStage.setWidth(1080 / 3);
        primaryStage.setHeight(1780 / 3);
        primaryStage.show();
    }
}
