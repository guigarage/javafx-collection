package com.guigarage.incubator.imageviewer.diashow;

import com.guigarage.animations.slideshow.SlideshowScene;
import com.guigarage.controls.AlwaysFitImageView;
import com.guigarage.incubator.imageviewer.data.ImageObject;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SimpleScene extends SlideshowScene {

    public SimpleScene(ImageObject imageObject) {
        this(new ImageView(imageObject.getOriginalImage()), null);
    }

    public SimpleScene(ImageObject imageObject, SlideshowScene nextScene) {
        this(new AlwaysFitImageView(imageObject.getOriginalImage()), nextScene);
    }

    public SimpleScene(ImageView node, SlideshowScene nextScene) {
        super(node, null, nextScene, Duration.seconds(4));
    }
}
