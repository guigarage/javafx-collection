package com.guigarage.demos.material;

import com.guigarage.material.AnimatedIcon;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Random;

/**
 * Created by hendrikebbers on 01.10.14.
 */
public class MaterialDesignButtonDemo extends Application {

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnimatedIcon icon = new AnimatedIcon();
        icon.setOnMouseClicked(e -> {
            int r = new Random().nextInt(6);
            if(r == 1) {
                icon.toPause();
            } else if(r == 2) {
                icon.toPlay();
            } else if(r == 3) {
                icon.toBack();
            } else if(r == 4) {
                icon.toMenu();
            } else if(r == 5) {
                icon.toClose();
            } else if(r == 6) {
                icon.toPlus();
            } else if(r == 7) {
                icon.toArrow();
            } else {
                icon.toMenu();
            }
        });
        StackPane p = new StackPane(icon);
        p.setScaleX(8);
        p.setScaleY(8);
        primaryStage.setScene(new Scene(p));
        primaryStage.setWidth(800);
        primaryStage.setHeight(800);
        primaryStage.show();
    }
}
