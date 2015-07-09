package com.guigarage.incubator.coverflow;

import com.guigarage.thirddimension.Util3D;
import javafx.animation.PathTransition;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class CoverflowCell<T> extends Group {

    private PathTransition transition;

    private Coverflow<T> coverflow;

    private double currentFraction;

    public CoverflowCell(Coverflow<T> coverflow) {
        this.coverflow = coverflow;
        Node imageNode = Util3D.createImageGroup(new Image(CoverflowCell.class.getResource("cover.jpg").toExternalForm()));
        imageNode.setRotationAxis(new Point3D(1, 0, 0));
        imageNode.setRotate(90);
        getChildren().add(imageNode);
        transition = new PathTransition(Duration.seconds(1), coverflow.getPath(), this);


        transition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        transition.play();
        transition.stop();
    }

    public void setToFraction(double fraction) {
        currentFraction = fraction;
        if(0 <= fraction && fraction <= 1.0) {
            if(getParent() == null) {
                coverflow.getChildren().add(this);
            }
            transition.interpolate(fraction);
            if (fraction < 0.2) {
                setOpacity(fraction * 5.0);
            } else if (fraction > 0.8) {
                setOpacity(0.2 - (fraction - 0.8) * 5.0);
            } else {
                setOpacity(1.0);
            }
        } else {
            coverflow.getChildren().remove(this);
        }
    }

    public double getCurrentFraction() {
        return currentFraction;
    }
}
