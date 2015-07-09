package com.guigarage.demos.window;

import com.guigarage.controls.MaximizePane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class NotepadDemo extends Application {

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        TextArea textArea = new TextArea();
        textArea.setMaxWidth(372);
        textArea.setMaxHeight(284);
        MaximizePane pane = new MaximizePane();
        pane.getChildren().add(textArea);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(NotepadDemo.class.getResource("notepad.css").toExternalForm());
        primaryStage.setResizable(false);
        primaryStage.setTitle("Notepad");
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }
}
