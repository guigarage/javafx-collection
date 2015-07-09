package com.guigarage.demos.animation;

import com.guigarage.demos.slideshow.SlideshowDemo;
import com.guigarage.imaging.ImageUtilities;
import com.guigarage.incubator.imageviewer.data.ImageObject;
import com.guigarage.ui.UiUtilities;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Animation3DDemo extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group mainGroup = new Group();

        Node imageGroup = createImageGroup(new ImageObject(SlideshowDemo.class.getResource("2.jpg").toExternalForm()));
        animate(imageGroup, Duration.ZERO);

        Node imageGroup2 = createImageGroup(new ImageObject(SlideshowDemo.class.getResource("3.jpg").toExternalForm()));
        imageGroup2.setTranslateX(-102);
        animate(imageGroup2, Duration.millis(800));

        Node imageGroup3 = createImageGroup(new ImageObject(SlideshowDemo.class.getResource("4.jpg").toExternalForm()));
        imageGroup3.setTranslateX(102);

        mainGroup.getChildren().addAll(imageGroup, imageGroup2, imageGroup3);


        UiUtilities.viewIn3DScene(primaryStage, mainGroup);

        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.show();
    }

    public void animate(Node imageGroup, Duration delay) {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(4), imageGroup);
        transition.setFromZ(-600);
        transition.setDelay(delay);
        transition.setToZ(0);
        transition.setCycleCount(Animation.INDEFINITE);
        transition.setAutoReverse(true);
        transition.play();
    }

    public Node createImageGroup(ImageObject imageObject) {
        Box box = new Box(100, 100, 0.1);
        Image texture = ImageUtilities.fastResize(imageObject.getOriginalImage(), 800, 800);
        box.setMaterial(createImageMaterial(imageObject));
        return box;
    }

    private Material createImageMaterial(ImageObject imageObject) {
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(
                imageObject.getOriginalImage()
        );
        return material;
    }
}

