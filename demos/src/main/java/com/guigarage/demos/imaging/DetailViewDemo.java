package com.guigarage.demos.imaging;

import com.guigarage.demos.slideshow.SlideshowDemo;
import com.guigarage.incubator.imageviewer.data.Album;
import com.guigarage.incubator.imageviewer.data.ImageObject;
import com.guigarage.incubator.imageviewer.views.DetailView;
import com.guigarage.ui.UiUtilities;
import javafx.application.Application;
import javafx.stage.Stage;

public class DetailViewDemo extends Application {

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
                new ImageObject(SlideshowDemo.class.getResource("8.jpg").toExternalForm())
        );

        DetailView detailView = new DetailView();
        detailView.setAlbum(album);
        UiUtilities.viewIn2DScene(primaryStage, detailView);
        primaryStage.setWidth(1015);
        primaryStage.setHeight(697);
        primaryStage.show();
    }
}
