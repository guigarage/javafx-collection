package com.guigarage.demos.scrollendless;

import com.sun.javafx.scene.control.skin.ListViewSkin;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.UUID;
import java.util.stream.Stream;

/**
 * Created by hendrikebbers on 06.11.14.
 */
public class ScrollDemo extends Application {

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ListView<String> list = new ListView<>();

        ObservableList<String> data = FXCollections.observableArrayList();
        for(int i = 0; i < 100; i++) {
            data.add(UUID.randomUUID().toString());
        }
        list.setItems(data);

        ListViewSkin<String> skin = new EndlessListSkin<>(list);
        list.setSkin(skin);
        primaryStage.setScene(new Scene(list));
        primaryStage.show();
    }
}
