package com.guigarage.incubator.coverflow;

import com.guigarage.ui.UiUtilities;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CoverflowDemo extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Coverflow f = new Coverflow();

        SubScene coverflowScene = UiUtilities.viewIn3DSubscene(f);
        coverflowScene.widthProperty().bind(primaryStage.widthProperty());
        coverflowScene.setHeight(600);

        BorderPane pane = new BorderPane();
        pane.setCenter(coverflowScene);

        Button toOne = new Button("0");
        toOne.setOnAction(e -> f.animateTo(0));

        Button toTen = new Button("10");
        toTen.setOnAction(e -> f.animateTo(10));
        pane.setBottom(new HBox(toOne, toTen));

        primaryStage.setScene(new Scene(pane));
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.show();
    }

}