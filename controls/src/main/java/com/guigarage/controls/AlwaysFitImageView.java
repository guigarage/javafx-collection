package com.guigarage.controls;

import com.guigarage.ui.UiUtilities;
import javafx.beans.InvalidationListener;
import javafx.geometry.Dimension2D;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

public class AlwaysFitImageView extends ImageView {

    private InvalidationListener doScaleListener;

    public AlwaysFitImageView(Image image) {
        super(image);
        init();
    }

    public AlwaysFitImageView(String url) {
        super(url);
        init();
    }

    public void init() {

        doScaleListener = e -> scale();

        imageProperty().addListener(doScaleListener);

        parentProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null && oldValue instanceof Region) {
                ((Region) oldValue).widthProperty().removeListener(doScaleListener);
                ((Region) oldValue).heightProperty().removeListener(doScaleListener);
            }
            if (newValue != null && newValue instanceof Region) {
                ((Region) newValue).widthProperty().addListener(doScaleListener);
                ((Region) newValue).heightProperty().addListener(doScaleListener);
                scale();
            }
        });
    }

    private void scale() {
        Parent parent = getParent();
        if(parent != null && parent instanceof Region) {
            Dimension2D newDim = UiUtilities.shouldFitIn(new Dimension2D(getImage().getWidth(), getImage().getHeight()), new Dimension2D(((Region) parent).getWidth(), ((Region) parent).getHeight()));
            setFitWidth(newDim.getWidth());
            setFitHeight(newDim.getHeight());
        }
    }
}
