package com.guigarage.demos.imaging;

import com.guigarage.controls.AlwaysFitImageView;
import com.guigarage.demos.slideshow.SlideshowDemo;
import com.guigarage.incubator.imageviewer.data.ImageObject;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class AlwaysFitImageViewDemo extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Image i = new ImageObject(SlideshowDemo.class.getResource("3.jpg").toExternalForm()).getOriginalImage();
        AlwaysFitImageView imageView = new AlwaysFitImageView(i);

        StackPane p = new StackPane(imageView);

        primaryStage.setScene(new Scene(p));
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.show();
    }

}

