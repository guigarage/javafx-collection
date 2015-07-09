package com.guigarage.demos.itunes.slideshow;

import com.guigarage.animations.slideshow.Slideshow;
import com.guigarage.animations.slideshow.SlideshowScene;
import com.guigarage.demos.itunes.data.Album;
import com.guigarage.incubator.imageviewer.data.ImageObject;
import com.guigarage.incubator.imageviewer.diashow.Scene3D;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hendrikebbers on 13.09.14.
 */
public class ITunesSlideshow {

    public void start(List<Album> albums) {
        List<ImageObject> imageObjects = new ArrayList<>();
        albums.forEach(a -> imageObjects.add(new ImageObject(a.getCoverUrl())));

        Slideshow diashow = new Slideshow(generateMixEffect(imageObjects), Duration.millis(2000));
        diashow.play();

        Stage stage = new Stage();
        stage.setScene(new Scene(diashow.getStage()));
        stage.setWidth(800);
        stage.setHeight(600);
        stage.show();
    }

    public SlideshowScene generateMixEffect(List<ImageObject> imageObjects) {
        SlideshowScene s = null;
        for (int i = 0; i < 30; i++) {
            s = new Scene3D(imageObjects, s);
        }
        return s;
    }

}
