package com.guigarage.demos.slideshow;

import com.guigarage.animations.slideshow.Slideshow;
import com.guigarage.animations.slideshow.SlideshowScene;
import com.guigarage.incubator.imageviewer.data.ImageObject;
import com.guigarage.incubator.imageviewer.diashow.KenBurnsScene;
import com.guigarage.incubator.imageviewer.diashow.Scene3D;
import com.guigarage.incubator.imageviewer.diashow.SimpleScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SlideshowDemo extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        List<ImageObject> imageObjects = new ArrayList<>();
        imageObjects.add(new ImageObject(SlideshowDemo.class.getResource("1.jpg").toExternalForm()));
        imageObjects.add(new ImageObject(SlideshowDemo.class.getResource("2.jpg").toExternalForm()));
        imageObjects.add(new ImageObject(SlideshowDemo.class.getResource("3.jpg").toExternalForm()));
        imageObjects.add(new ImageObject(SlideshowDemo.class.getResource("4.jpg").toExternalForm()));
        imageObjects.add(new ImageObject(SlideshowDemo.class.getResource("5.jpg").toExternalForm()));
        imageObjects.add(new ImageObject(SlideshowDemo.class.getResource("6.jpg").toExternalForm()));
        imageObjects.add(new ImageObject(SlideshowDemo.class.getResource("7.jpg").toExternalForm()));

        Slideshow diashow = new Slideshow(generateMixEffect(imageObjects), Duration.millis(2000));
        diashow.play();
        primaryStage.setScene(new Scene(diashow.getStage()));
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.show();
    }

    public SlideshowScene generateSimpleEffect(List<ImageObject> imageObjects) {
        SlideshowScene s = null;
        for (ImageObject o : imageObjects) {
            s = new SimpleScene(o, s);
        }
        return s;
    }

    public SlideshowScene generateKenBurnsEffect(List<ImageObject> imageObjects) {
        SlideshowScene s = null;
        for (ImageObject o : imageObjects) {
            s = new KenBurnsScene(o, s);
        }
        return s;
    }

    public SlideshowScene generate3DEffect(List<ImageObject> imageObjects) {
        SlideshowScene s = null;
        for (int i = 0; i < 10; i++) {
            s = new Scene3D(imageObjects, s);
        }
        return s;
    }

    public SlideshowScene generateMixEffect(List<ImageObject> imageObjects) {
        SlideshowScene s = null;
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < 30; i++) {
            if (r.nextBoolean()) {
                s = new Scene3D(imageObjects, s);
            } else {
                ImageObject o = imageObjects.get(r.nextInt(imageObjects.size()));
                s = new KenBurnsScene(o, s);
            }
        }
        return s;
    }

}


