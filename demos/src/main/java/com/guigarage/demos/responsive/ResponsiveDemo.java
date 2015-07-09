package com.guigarage.demos.responsive;

import com.guigarage.controls.IconifiedButtonSkin;
import com.guigarage.responsive.ResponsiveHandler;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.UUID;


public class ResponsiveDemo extends Application {

    public static void main(String... args) {
        launch(args);
    }

    @Override public void start(Stage primaryStage) throws Exception {

        FlowPane myToolbar = new FlowPane();
        myToolbar.setId("toolbar");
        myToolbar.setRowValignment(VPos.BOTTOM);

        Button iconButton = new Button();
        IconifiedButtonSkin.addStyleClass(iconButton);
        iconButton.setId("application-icon");
        myToolbar.getChildren().add(iconButton);

        Button contactsButton = new Button();
        contactsButton.getStyleClass().addAll("visible-lg", "visible-md", "visible-sm", "toolbar-button");
        IconifiedButtonSkin.addStyleClass(contactsButton);
        contactsButton.setId("contacts-button");
        myToolbar.getChildren().add(contactsButton);
        Button uploadButton = new Button();
        uploadButton.getStyleClass().addAll("visible-lg", "visible-md", "visible-sm", "toolbar-button");
        IconifiedButtonSkin.addStyleClass(uploadButton);
        uploadButton.setId("upload-button");
        myToolbar.getChildren().add(uploadButton);
        Button settingsButton = new Button();
        settingsButton.getStyleClass().addAll("visible-lg", "visible-md", "visible-sm", "toolbar-button");
        IconifiedButtonSkin.addStyleClass(settingsButton);
        settingsButton.setId("settings-button");
        myToolbar.getChildren().add(settingsButton);

        ObservableList<String> items = FXCollections.observableArrayList();
        for (int i = 0; i < 1000; i++) {
            items.addAll(UUID.randomUUID().toString());
        }


        TableView<String> table = new TableView<>(items);
        TableColumn<String, String> column1 = new TableColumn<>("Text");
        column1.setCellValueFactory(s -> new SimpleObjectProperty<String>(s.getValue()));
        TableColumn<String, Integer> column2 = new TableColumn<>("Lenght");
        column2.setCellValueFactory(s -> new SimpleObjectProperty<Integer>(s.getValue().length()));
        TableColumn<String, Integer> column3 = new TableColumn<>("Hash");
        column3.setCellValueFactory(s -> new SimpleObjectProperty<Integer>(s.getValue().hashCode()));
        table.getColumns().addAll(column1, column2, column3);
        table.getStyleClass().addAll("visible-lg", "visible-md");

        ListView list = new ListView(items);
        list.getStyleClass().addAll("visible-xs", "visible-sm");

        StackPane centerPane = new StackPane(table, list);
        centerPane.setId("main-pane");
        VBox.setVgrow(centerPane, Priority.ALWAYS);

        VBox pane = new VBox(myToolbar, centerPane);
        Scene scene = new Scene(pane);

        primaryStage.setScene(scene);
        ResponsiveHandler.addResponsiveToWindow(primaryStage);
        primaryStage.setOnCloseRequest(e -> System.exit(0));

        IconifiedButtonSkin.addStylesheet(scene);
        scene.getStylesheets().add(ResponsiveDemo.class.getResource("demo-skin.css").toExternalForm());

        primaryStage.show();
    }

    private Button createLargeActionButton(String text) {
        Button b = new Button(text);
        b.getStyleClass().addAll("visible-lg", "visible-md", "visible-sm", "toolbar-button");
        return b;
    }
}
