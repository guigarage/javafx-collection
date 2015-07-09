package com.guigarage.incubator.imageviewer.diashow;

import com.guigarage.animations.slideshow.SlideshowScene;
import com.guigarage.animations.transitions.DurationBasedTransition;
import com.guigarage.incubator.imageviewer.data.ImageObject;
import com.guigarage.thirddimension.Util3D;
import com.guigarage.ui.UiUtilities;
import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.util.List;
import java.util.Random;

public class Scene3D extends SlideshowScene {

    public Scene3D(List<ImageObject> imageObjects) {
        this(imageObjects, Scene3DType.random());
    }

    public Scene3D(List<ImageObject> imageObjects, Scene3DType type) {
        this(imageObjects, null, type);
    }

    public Scene3D(List<ImageObject> imageObjects, SlideshowScene nextScene) {
        this(imageObjects, nextScene, Scene3DType.random());
    }

    public Scene3D(List<ImageObject> imageObjects, SlideshowScene nextScene, Scene3DType type) {
        this(create3DObjects(imageObjects), nextScene, type);
    }

    private Scene3D(Group group3D, SlideshowScene nextScene, Scene3DType type) {
        super(wrap(UiUtilities.viewIn3DSubscene(group3D)), createAnimation(group3D, type), nextScene, Duration.seconds(4));
    }

    private static StackPane wrap(SubScene subScene) {
        StackPane pane = new StackPane();
        pane.getChildren().add(subScene);
        subScene.widthProperty().bind(pane.widthProperty());
        subScene.heightProperty().bind(pane.heightProperty());
        pane.setStyle("-fx-background-color: white");
        return pane;
    }

    private static Animation createAnimation(Group group3D, Scene3DType type) {
        if(type.equals(Scene3DType.A)) {
            return createAnimationA(group3D);
        } else if(type.equals(Scene3DType.B)) {
            return createAnimationB(group3D);
        } else if(type.equals(Scene3DType.C)) {
            return createAnimationC(group3D);
        } else if(type.equals(Scene3DType.D)) {
            return createAnimationD(group3D);
        }
        return createAnimationE(group3D);
    }

    private static Animation createAnimationA(Group group3D) {
        group3D.setRotationAxis(new Point3D(1, 0, 0));
        group3D.setRotate(-45);
        group3D.setTranslateZ(-500);

        TranslateTransition t  = new TranslateTransition(Duration.seconds(8), group3D);
        t.setFromZ(-500);
        t.setToZ(-550);
        return t;
    }

    private static Animation createAnimationB(Group group3D) {
        group3D.setRotationAxis(new Point3D(1, 0, 0));
        group3D.setRotate(-45);
        group3D.setTranslateZ(-540);

        RotateTransition t = new RotateTransition(Duration.seconds(8), group3D);
        t.setAxis(new Point3D(0, 0, 1));
        t.setFromAngle(-30);
        t.setToAngle(30);
        TranslateTransition t2  = new TranslateTransition(Duration.seconds(8), group3D);
        t2.setFromZ(-540);
        t2.setToZ(-580);

        return new ParallelTransition(t, t2);
    }

    private static Animation createAnimationC(Group group3D) {
        group3D.setTranslateZ(-500);

        Transform rotationTransform = new Rotate(-30, new Point3D(1, 0, 0));

        Translate translateTransform = new Translate(0, -70, 0);
        group3D.getTransforms().addAll(translateTransform, rotationTransform);

        DurationBasedTransition t = new DurationBasedTransition(Duration.seconds(8)) {
            @Override
            protected void interpolate(double frac) {
                translateTransform.setY(-70 + (90 * frac));
            }
        };
        return t;
    }

    private static Animation createAnimationD(Group group3D) {
        group3D.setTranslateZ(-500);

        Transform rotationTransform = new Rotate(-30, new Point3D(1, 0, 0));

        Translate translateTransform = new Translate(0, -70, 0);
        group3D.getTransforms().addAll(translateTransform, rotationTransform);

        DurationBasedTransition t = new DurationBasedTransition(Duration.seconds(8)) {
            @Override
            protected void interpolate(double frac) {
                translateTransform.setY(-70 + (90 * frac));
            }
        };

        Node n = findImageGroup(group3D, Pos.CENTER);
        TranslateTransition t2 = new TranslateTransition(Duration.seconds(4), n);
        t2.setFromZ(-60);
        t2.setToZ(0);

        return new ParallelTransition(t, t2);
    }

    private static Animation createAnimationE(Group group3D) {
        group3D.setTranslateZ(-500);
        group3D.setRotationAxis(new Point3D(0, 0, 1));
        group3D.setRotate(4);

        TranslateTransition t1 = new TranslateTransition(Duration.seconds(8), group3D.getChildren().get(1));
        t1.setFromX(-30);
        t1.setToX(30);

        TranslateTransition t2 = new TranslateTransition(Duration.seconds(8), group3D.getChildren().get(2));
        t2.setFromX(30);
        t2.setToX(-30);

        return new ParallelTransition(t1, t2);
    }


    private static Node findImageGroup(Parent parent, Pos p) {
        for(Node child : parent.getChildrenUnmodifiable()) {
            if(child.getProperties().containsKey("imageGroup")) {
                if(child.getProperties().get("imageGroup") != null && child.getProperties().get("imageGroup").equals(p)){
                    return child;
                }
            }
            if(child instanceof Parent) {
                Node n = findImageGroup((Parent) child, p);
                if(n != null) {
                    return n;
                }
            }
        }
        return null;
    }

    private static Group create3DObjects(List<ImageObject> imageObjects) {
        Group mainGroup = new Group();

        int startIndex = new Random(System.currentTimeMillis()).nextInt(imageObjects.size());

        Node imageGroupCenter = Util3D.createImageGroup(getObjectWithOverrun(imageObjects, startIndex++).getOriginalImage());
        Node imageGroupCenterLeft = Util3D.createImageGroup(getObjectWithOverrun(imageObjects, startIndex++).getOriginalImage());
        imageGroupCenterLeft.setTranslateX(-102);
        Node imageGroupCenterRight = Util3D.createImageGroup(getObjectWithOverrun(imageObjects, startIndex++).getOriginalImage());
        imageGroupCenterRight.setTranslateX(102);
        Group centerGroup = new Group(imageGroupCenter, imageGroupCenterLeft, imageGroupCenterRight);

        Node imageGroupTop = Util3D.createImageGroup(getObjectWithOverrun(imageObjects, startIndex++).getOriginalImage());
        Node imageGroupTopLeft = Util3D.createImageGroup(getObjectWithOverrun(imageObjects, startIndex++).getOriginalImage());
        imageGroupTopLeft.setTranslateX(-102);
        Node imageGroupTopRight = Util3D.createImageGroup(getObjectWithOverrun(imageObjects, startIndex++).getOriginalImage());
        imageGroupTopRight.setTranslateX(102);
        Group topGroup = new Group(imageGroupTop, imageGroupTopLeft, imageGroupTopRight);
        topGroup.setTranslateY(-102);

        Node imageGroupBottom = Util3D.createImageGroup(getObjectWithOverrun(imageObjects, startIndex++).getOriginalImage());
        Node imageGroupBottomLeft = Util3D.createImageGroup(getObjectWithOverrun(imageObjects, startIndex++).getOriginalImage());
        imageGroupBottomLeft.setTranslateX(-102);
        Node imageGroupBottomRight = Util3D.createImageGroup(getObjectWithOverrun(imageObjects, startIndex++).getOriginalImage());
        imageGroupBottomRight.setTranslateX(102);
        Group bottomGroup = new Group(imageGroupBottom, imageGroupBottomLeft, imageGroupBottomRight);
        bottomGroup.setTranslateY(102);

        mainGroup.getChildren().addAll(centerGroup, topGroup, bottomGroup);

        imageGroupCenter.getProperties().put("imageGroup", Pos.CENTER);
        imageGroupCenterLeft.getProperties().put("imageGroup", Pos.CENTER_LEFT);
        imageGroupCenterRight.getProperties().put("imageGroup", Pos.CENTER_RIGHT);
        imageGroupTop.getProperties().put("imageGroup", Pos.TOP_CENTER);
        imageGroupTopLeft.getProperties().put("imageGroup", Pos.TOP_LEFT);
        imageGroupTopRight.getProperties().put("imageGroup", Pos.TOP_RIGHT);
        imageGroupBottom.getProperties().put("imageGroup", Pos.BOTTOM_CENTER);
        imageGroupBottomLeft.getProperties().put("imageGroup", Pos.BOTTOM_LEFT);
        imageGroupBottomRight.getProperties().put("imageGroup", Pos.BOTTOM_RIGHT);

        return mainGroup;
    }

    private static <T> T getObjectWithOverrun(List<T> list, int index) {
        while(index > list.size() - 1) {
            index = index - list.size();
        }
        return list.get(index);
    }

}
