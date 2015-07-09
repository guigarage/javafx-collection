package com.guigarage.demos.imaging;

import com.guigarage.demos.slideshow.SlideshowDemo;
import com.guigarage.incubator.imageviewer.data.Album;
import com.guigarage.incubator.imageviewer.data.ImageObject;
import com.guigarage.incubator.imageviewer.views.AlbumOverview;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GridDemo extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Album album = new Album(
                new ImageObject(SlideshowDemo.class.getResource("1.jpg").toExternalForm()),
                new ImageObject(SlideshowDemo.class.getResource("2.jpg").toExternalForm()),
                new ImageObject(SlideshowDemo.class.getResource("3.jpg").toExternalForm()),
                new ImageObject(SlideshowDemo.class.getResource("4.jpg").toExternalForm()),
                new ImageObject(SlideshowDemo.class.getResource("5.jpg").toExternalForm()),
                new ImageObject(SlideshowDemo.class.getResource("6.jpg").toExternalForm()),
                new ImageObject(SlideshowDemo.class.getResource("7.jpg").toExternalForm()),
                new ImageObject(SlideshowDemo.class.getResource("8.jpg").toExternalForm()),
                new ImageObject(SlideshowDemo.class.getResource("1.jpg").toExternalForm()),
                new ImageObject(SlideshowDemo.class.getResource("2.jpg").toExternalForm()),
                new ImageObject(SlideshowDemo.class.getResource("3.jpg").toExternalForm()),
                new ImageObject(SlideshowDemo.class.getResource("4.jpg").toExternalForm()),
                new ImageObject(SlideshowDemo.class.getResource("5.jpg").toExternalForm()),
                new ImageObject(SlideshowDemo.class.getResource("6.jpg").toExternalForm()),
                new ImageObject(SlideshowDemo.class.getResource("7.jpg").toExternalForm()),
                new ImageObject(SlideshowDemo.class.getResource("8.jpg").toExternalForm()),
                new ImageObject(SlideshowDemo.class.getResource("1.jpg").toExternalForm()),
                new ImageObject(SlideshowDemo.class.getResource("2.jpg").toExternalForm()),
                new ImageObject(SlideshowDemo.class.getResource("3.jpg").toExternalForm()),
                new ImageObject(SlideshowDemo.class.getResource("4.jpg").toExternalForm()),
                new ImageObject(SlideshowDemo.class.getResource("5.jpg").toExternalForm()),
                new ImageObject(SlideshowDemo.class.getResource("6.jpg").toExternalForm()),
                new ImageObject(SlideshowDemo.class.getResource("7.jpg").toExternalForm()),
                new ImageObject(SlideshowDemo.class.getResource("8.jpg").toExternalForm())
        );

        AlbumOverview overview = new AlbumOverview();
        overview.setAlbum(album);
        primaryStage.setScene(new Scene(overview));
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.show();

    }
}
