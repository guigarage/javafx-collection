package com.guigarage.incubator.imageviewer.diashow;

import com.guigarage.animations.slideshow.SlideshowScene;
import com.guigarage.animations.transitions.KenBurnsTransition;
import com.guigarage.controls.AlwaysFitImageView;
import com.guigarage.incubator.imageviewer.data.ImageObject;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class KenBurnsScene extends SlideshowScene {

    public KenBurnsScene(ImageObject imageObject) {
        this(new ImageView(imageObject.getOriginalImage()), null);
    }

    public KenBurnsScene(ImageObject imageObject, SlideshowScene nextScene) {
        this(new AlwaysFitImageView(imageObject.getOriginalImage()), nextScene);
    }

    public KenBurnsScene(ImageView node, SlideshowScene nextScene) {
        super(node, new KenBurnsTransition(node, Duration.seconds(7)), nextScene, Duration.seconds(4));
    }
}
