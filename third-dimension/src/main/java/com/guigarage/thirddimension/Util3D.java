package com.guigarage.thirddimension;

import com.guigarage.imaging.ImageUtilities;
import com.guigarage.ui.UiUtilities;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Util3D {

    public static Node createImageGroup(Image image) {
        Box box = new Box(100, 100, 0.01);
        Dimension2D imageDim = UiUtilities.shouldFitIn(new Dimension2D(image.getWidth(), image.getHeight()), new Dimension2D(1000, 1000));
        Image texture = ImageUtilities.fastResize(image, (int) imageDim.getWidth(), (int) imageDim.getHeight());
        box.setMaterial(createImageMaterial(texture));
        return box;
    }

    public static Material createImageMaterial(Image image) {
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(image);
        return material;
    }
}
