package com.guigarage.demos.flatter;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import com.guigarage.flatterfx.FlatterFX;
import com.guigarage.flatterfx.overlay.DefaultOverlay;

public class ComboBoxDemo extends Application {

    @Override public void start(Stage primaryStage) throws Exception {
        ComboBox<String> comboBox = new ComboBox<>();
        ObservableList<String> items = FXCollections.observableArrayList();
        comboBox.setItems(items);
        comboBox.getItems().add("Luke Skywalker");
        comboBox.getItems().add("Han Solo");
        comboBox.getItems().add("Boba Fett");
        comboBox.getItems().add("Darth Vader");
        comboBox.getItems().add("R2D2");
        comboBox.getItems().add("C3P0");
        comboBox.getItems().add("Jabba the Hut");
        comboBox.getItems().add("Chewie");
        comboBox.getItems().add("Mara Jade");
        comboBox.getItems().add("Bib Fortuna");

        StackPane myPane = new StackPane();
        myPane.getChildren().add(new ImageView(ComboBoxDemo.class.getResource("background.png").toString()));
        myPane.getChildren().add(comboBox);
        DefaultOverlay overlay = new DefaultOverlay();
        myPane.getChildren().add(overlay);

        Scene myScene = new Scene(myPane);
        FlatterFX.getInstance().setOverlayLayerForScene(myScene, overlay);
        FlatterFX.style();

        primaryStage.setScene(myScene);
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.show();
        comboBox.getSelectionModel().clearAndSelect(0);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
