package com.guigarage.imaging;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.NonInvertibleTransformException;

public class ImageViewer extends StackPane {

    private double mousePosX;
    private double mousePosY;
    private Pane imageRegion;
    private ImageView imageView;

    private Image unfilteredImage;

    private ObjectProperty<ImageFilter> filter;

    public ImageViewer(Image image) {
        imageRegion = new StackPane();
        getChildren().add(imageRegion);

        imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.fitWidthProperty().bind(widthProperty());
        imageView.fitHeightProperty().bind(heightProperty());
        imageRegion.getChildren().add(imageView);

        setOnMousePressed(e -> {
            mousePosX = e.getSceneX();
            mousePosY = e.getSceneY();

            if (e.isSecondaryButtonDown()) {
                Point2D pointOnRegion = null;
                try {
                    pointOnRegion = imageRegion.getLocalToSceneTransform().inverseTransform(e.getSceneX(), e.getSceneY());
                } catch (NonInvertibleTransformException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                imageRegion.getChildren().add(new Rectangle(pointOnRegion.getX() - 5, pointOnRegion.getY() - 5, 10, 10));
            }
        });

        setOnZoom(e -> {
            Point2D pointOnImage = null;
            try {
                pointOnImage = imageRegion.getLocalToSceneTransform().inverseTransform(e.getSceneX(), e.getSceneY());
            } catch (NonInvertibleTransformException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            zoom(e.getZoomFactor(), pointOnImage);
        });

        setOnMouseDragged(e -> {
            double mouseDeltaX = (e.getSceneX() - mousePosX);
            double mouseDeltaY = (e.getSceneY() - mousePosY);
            mousePosX = e.getSceneX();
            mousePosY = e.getSceneY();

            imageRegion.setTranslateX(imageRegion.getTranslateX() + mouseDeltaX);
            imageRegion.setTranslateY(imageRegion.getTranslateY() + mouseDeltaY);
        });
        filter = new SimpleObjectProperty<>();
        filter.addListener(e -> setImage(unfilteredImage));
    }

    public void zoom(double zoomFactor, Point2D pointOnImage) {
        double currentX = pointOnImage.getX();
        double currentY = pointOnImage.getY();

        double currentDistanceFromCenterX = currentX - imageRegion.getBoundsInLocal().getWidth() / 2;
        double currentDistanceFromCenterY = currentY - imageRegion.getBoundsInLocal().getHeight() / 2;

        double addScaleX = currentDistanceFromCenterX * zoomFactor;
        double addScaleY = currentDistanceFromCenterY * zoomFactor;

        double translationX = addScaleX - currentDistanceFromCenterX;
        double translationY = addScaleY - currentDistanceFromCenterY;

        imageRegion.setTranslateX(imageRegion.getTranslateX() - translationX * imageRegion.getScaleX());
        imageRegion.setTranslateY(imageRegion.getTranslateY() - translationY * imageRegion.getScaleY());

        imageRegion.setScaleX(imageRegion.getScaleX() * zoomFactor);
        imageRegion.setScaleY(imageRegion.getScaleY() * zoomFactor);
    }

    public void setImage(Image image) {
        unfilteredImage = image;
        if (filter.get() != null && image != null) {
            imageView.setImage(ImageUtilities.filter(image, filter.get()));
        } else {
            imageView.setImage(image);
        }
        imageRegion.setTranslateX(0);
        imageRegion.setTranslateY(0);
        imageRegion.setScaleX(1);
        imageRegion.setScaleY(1);
    }

    public ImageFilter getFilter() {
        return filter.get();
    }

    public void setFilter(ImageFilter filter) {
        this.filter.set(filter);
    }

    public ObjectProperty<ImageFilter> filterProperty() {
        return filter;
    }
}
