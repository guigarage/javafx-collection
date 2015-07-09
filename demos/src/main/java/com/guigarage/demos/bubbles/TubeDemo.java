package com.guigarage.demos.bubbles;

import com.guigarage.incubator.bubbles.LabTube;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class TubeDemo extends Application {


    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        LabTube tube = new LabTube();
        tube.setScaleX(0.5);
        tube.setScaleY(0.5);

        Scene scene = new Scene(new StackPane(tube));
        scene.setFill(Color.WHITESMOKE);
        primaryStage.setScene(scene);
        primaryStage.setOnHidden(e -> System.exit(0));
        primaryStage.show();
    }


}
