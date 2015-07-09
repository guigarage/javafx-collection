package com.guigarage.demos.window;

import com.guigarage.window.WindowRegion;
import com.guigarage.window.WindowStylerUtilities;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Demo extends Application {

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        WindowRegion region = new WindowRegion();
        region.setContent(new StackPane(new Button("click me!")));

        Scene myScene = WindowStylerUtilities.initForWindowStyler(primaryStage, region);

        myScene.getStylesheets().add(Demo.class.getResource("canoo.css").toExternalForm());
        //myScene.getStylesheets().add(Demo.class.getResource("notepad.css").toExternalForm());

        primaryStage.setTitle("Canoo Labs");

        primaryStage.show();
    }
}
