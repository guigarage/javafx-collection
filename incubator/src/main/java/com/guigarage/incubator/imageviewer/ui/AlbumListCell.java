package com.guigarage.incubator.imageviewer.ui;

import com.guigarage.incubator.imageviewer.data.Album;
import com.guigarage.incubator.imageviewer.data.ImageObject;
import com.guigarage.ui.UiUtilities;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.scene.control.ListCell;
import javafx.scene.text.Text;

public class AlbumListCell extends ListCell<Album> {

    private InvalidationListener updateListener;

    private ListChangeListener<ImageObject> imagesListener;

    public AlbumListCell() {
        updateListener = e -> update();
        imagesListener = e -> update();

        itemProperty().addListener((observer, oldValue, newValue) -> {
            if(oldValue != null) {
                oldValue.nameProperty().removeListener(updateListener);
                oldValue.iconProperty().removeListener(updateListener);
                oldValue.getImages().removeListener(imagesListener);
            }
            if(newValue != null) {
                newValue.nameProperty().addListener(updateListener);
                newValue.iconProperty().addListener(updateListener);
                newValue.getImages().addListener(imagesListener);
            }
            update();
        });
    }

    public void update() {
        if(getItem() != null) {
            setText(getItem().getName() + " (" + getItem().getImages().size() + ")");
            Text icon = UiUtilities.createIconText(getItem().getIcon(), 32);
            icon.fillProperty().bind(textFillProperty());
            setGraphic(icon);
        } else {
            setText(null);
            setGraphic(null);
        }
    }
}
