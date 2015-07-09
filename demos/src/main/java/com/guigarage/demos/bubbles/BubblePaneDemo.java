package com.guigarage.demos.bubbles;

import com.guigarage.incubator.bubbles.BubblePane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by hendrikebbers on 17.09.14.
 */
public class BubblePaneDemo extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        BubblePane pane = new BubblePane();
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }

    public static void main(String... args) {
        launch(args);
    }
}
