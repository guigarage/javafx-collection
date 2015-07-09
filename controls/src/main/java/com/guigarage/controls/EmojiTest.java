package com.guigarage.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class EmojiTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        TextField textField = new TextField();
        primaryStage.setScene(new Scene(new StackPane(textField)));
        primaryStage.show();
    }

    public static void main(String... args) {
        launch(args);
    }
}
