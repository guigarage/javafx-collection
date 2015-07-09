package com.guigarage.incubator.imageviewer.ui;

import com.guigarage.incubator.imageviewer.data.ImageObject;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.controlsfx.control.GridCell;


public class ImageObjectGridCell extends GridCell<ImageObject> {

    private ImageView imageView;

    private ChangeListener<Image> updateImageListener;

    public ImageObjectGridCell() {
        imageView = new ImageView();
        imageView.setPreserveRatio(false);
        imageView.setSmooth(false);
        setGraphic(imageView);

        updateImageListener = (obs, oldV, newV) -> {
            if (newV != null) {
                imageView.setImage(newV);
            }
        };

        itemProperty().addListener((obs, oldV, newV) -> {
            if (oldV != null) {
                oldV.cachedImageProperty().removeListener(updateImageListener);
            }
            if (newV != null) {
                newV.cachedImageProperty().addListener(updateImageListener);
            }
            update();
        });

        InvalidationListener sizeListener = (e) -> {
            update();
        };

        widthProperty().addListener(sizeListener);
        heightProperty().addListener(sizeListener);

        //Rectangle clip = new Rectangle();
        //clip.setStyle("-fx-arc-height: 40;" +
        //        "-fx-arc-width: 40;");
        //clip.widthProperty().bind(widthProperty().divide(2));
        //clip.heightProperty().bind(heightProperty().divide(2));
        //setClip(new Circle(40));
    }

    private void update() {
        if (getItem() != null && getWidth() > 0 && getHeight() > 0) {
            imageView.setImage(getItem().getForSize((int) getWidth(), (int) getHeight()));
        } else {
            imageView.setImage(null);
        }
    }

}