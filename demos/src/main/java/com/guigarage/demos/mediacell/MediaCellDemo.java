package com.guigarage.demos.mediacell;

import com.guigarage.controls.Media;
import com.guigarage.controls.SimpleMediaListCell;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.UUID;

public class MediaCellDemo extends Application {

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ListView<DemoMedia> listView = new ListView<>();
        for (int i = 0; i < 100; i++) {
            listView.getItems().add(new DemoMedia(UUID.randomUUID().toString()));
        }
        listView.setCellFactory(e -> new SimpleMediaListCell<>());
        StackPane pane = new StackPane(listView);
        pane.setPadding(new Insets(4));
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(MediaCellDemo.class.getResource("skin.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        primaryStage.show();
    }
}

class DemoMedia implements Media {

    private StringProperty title;

    private StringProperty description;

    private ObjectProperty<Image> image;

    DemoMedia(String description) {
        this.description = new SimpleStringProperty(description);
        this.title = new SimpleStringProperty("Title");
        this.image = new SimpleObjectProperty<>(new Image(DemoMedia.class.getResource("guigarage.png").toExternalForm()));
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public Image getImage() {
        return image.get();
    }

    public ObjectProperty<Image> imageProperty() {
        return image;
    }
}