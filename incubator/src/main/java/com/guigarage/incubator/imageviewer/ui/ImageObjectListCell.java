package com.guigarage.incubator.imageviewer.ui;

import com.guigarage.incubator.imageviewer.data.ImageObject;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageObjectListCell extends ListCell<ImageObject> {

    private ImageView imageView;

    private ChangeListener<Image> updateImageListener;

    public ImageObjectListCell() {
        imageView = new ImageView();
        imageView.setPreserveRatio(false);
        imageView.setSmooth(false);
        setGraphic(imageView);

        setStyle("-fx-background-color: transparent");

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
    }

    private void update() {
        if (getItem() != null && getWidth() > 0 && getHeight() > 0) {
            imageView.setImage(getItem().getForSize(100, 100));
        } else {
            imageView.setImage(null);
        }
    }

}