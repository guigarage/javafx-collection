package com.guigarage.ui;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class UiUtilities {

    public static Label createLabel(String text, Character icon) {
        Label label = new Label(text);
        label.setTextFill(Color.GRAY);
        label.setFont(new Font(20));
        label.setGraphicTextGap(6);
        Text iconT = createIconText(icon, 24);
        iconT.setFill(Color.GRAY);
        label.setGraphic(iconT);
        return label;
    }

    public static Text createIconText(Character c, double fontSize) {
        Text iconT = new Text(c + "");
        iconT.setFont(Font.loadFont(UiUtilities.class.getResourceAsStream("fontawesome-webfont.ttf"), fontSize));
        return iconT;
    }

    public static Font create(Font font, double size) {
        return new Font(font.getName(), size);
    }

    public static void add3DNavigation(SubScene scene, Node node) {

        new Runnable() {

            private Point2D mousePoint = new Point2D(0, 0);
            private Rotate xRotate = new Rotate(0, 0, 0, 0, new Point3D(1, 0, 0));
            private Rotate yRotate = new Rotate(0, 0, 0, 0, new Point3D(0, 1, 0));

            @Override
            public void run() {
                node.getTransforms().addAll(xRotate, yRotate);

                scene.setOnMousePressed(e -> {
                    mousePoint = new Point2D(e.getSceneX(), e.getSceneY());
                });

                scene.setOnZoom((e) -> {
                    System.out.println(node.getTranslateZ());
                    node.setTranslateZ(node.getTranslateZ() + (1.0 - e.getZoomFactor()) * 100);
                });

                scene.setOnMouseDragged(e -> {
                    double mouseDeltaX = (e.getSceneX() - mousePoint.getX());
                    double mouseDeltaY = (e.getSceneY() - mousePoint.getY());
                    mousePoint = new Point2D(e.getSceneX(), e.getSceneY());

                    xRotate.setAngle(xRotate.getAngle() - mouseDeltaY * 0.1 * 2.0);
                    yRotate.setAngle(yRotate.getAngle() + mouseDeltaX * 0.1 * 2.0);
                });
            }
        }.run();
    }

    public static void add3DNavigation(Scene scene, Node node) {
        new Runnable() {

            private Point2D mousePoint = new Point2D(0, 0);
            private Rotate xRotate = new Rotate(0, 0, 0, 0, new Point3D(1, 0, 0));
            private Rotate yRotate = new Rotate(0, 0, 0, 0, new Point3D(0, 1, 0));

            @Override
            public void run() {
                node.getTransforms().addAll(xRotate, yRotate);

                scene.setOnMousePressed(e -> {
                    mousePoint = new Point2D(e.getSceneX(), e.getSceneY());
                });

                scene.setOnZoom((e) -> {
                    System.out.println(node.getTranslateZ());
                    node.setTranslateZ(node.getTranslateZ() + (1.0 - e.getZoomFactor()) * 100);
                });

                scene.setOnMouseDragged(e -> {
                    double mouseDeltaX = (e.getSceneX() - mousePoint.getX());
                    double mouseDeltaY = (e.getSceneY() - mousePoint.getY());
                    mousePoint = new Point2D(e.getSceneX(), e.getSceneY());

                    xRotate.setAngle(xRotate.getAngle() - mouseDeltaY * 0.1 * 2.0);
                    yRotate.setAngle(yRotate.getAngle() + mouseDeltaX * 0.1 * 2.0);
                });
            }
        }.run();
    }

    public static void viewIn2DScene(Stage stage, Parent content) {
        stage.setScene(new Scene(content));
    }

    public static Group convertTo3D(Node node) {
        return convertTo3D(node, 0);
    }

    public static Group convertTo3D(Node node, int depth) {
        Group root = new Group();
        root.setTranslateX(node.getLayoutX());
        root.setTranslateY(node.getLayoutY());
        root.setTranslateZ(-20);

        System.out.println("Layer " + depth + " - Node Type: " + node.getClass());

        Box box = new Box(node.getBoundsInParent().getWidth(), node.getBoundsInParent().getHeight(), 0.1);
        box.setTranslateX(node.getLayoutX());
        box.setTranslateY(node.getLayoutY());


        SnapshotParameters snapshotParameters = new SnapshotParameters();
        snapshotParameters.setFill(Color.TRANSPARENT);
        box.setMaterial(new PhongMaterial(Color.WHITE, node.snapshot(snapshotParameters, new WritableImage((int) node.getBoundsInParent().getWidth(), (int) node.getBoundsInParent().getHeight())), null, null, null));
        root.getChildren().add(box);

        if (node instanceof Parent) {
            for (Node child : ((Parent) node).getChildrenUnmodifiable()) {
                root.getChildren().add(convertTo3D(child, depth + 1));
            }
        }
        return root;
    }

    public static void viewIn3DScene(Stage stage, Parent content, boolean navigation) {
        Group root = new Group();
        Group world = new Group();
        world.getChildren().add(content);
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setTranslateZ(-800);
        camera.setRotationAxis(new Point3D(1, 0, 0));
        world.getChildren().add(camera);
        root.getChildren().addAll(world, camera);
        Scene myScene = new Scene(root, -1, -1, true, SceneAntialiasing.BALANCED);
        myScene.setCamera(camera);

        if (navigation) {
            UiUtilities.add3DNavigation(myScene, world);
        }
        stage.setScene(myScene);
    }

    public static void viewIn3DScene(Stage stage, Parent content) {
        viewIn3DScene(stage, content, true);
    }

    public static SubScene viewIn3DSubscene(Parent content, boolean navigation) {
        Group root = new Group();
        Group world = new Group();
        world.getChildren().add(content);
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setTranslateZ(-800);
        camera.setRotationAxis(new Point3D(1, 0, 0));
        world.getChildren().add(camera);
        root.getChildren().addAll(world, camera);

        SubScene subScene = new SubScene(root, -1, -1, true, SceneAntialiasing.BALANCED);
        subScene.setCamera(camera);
        if (navigation) {
            UiUtilities.add3DNavigation(subScene, world);
        }
        return subScene;
    }

    public static SubScene viewIn3DSubscene(Parent content) {
        return viewIn3DSubscene(content, true);
    }

    public static Dimension2D shouldFitIn(double originalWidth, double originalHeight, double toFitWidth, double toFitHeight) {
        double fitRatio = toFitWidth / toFitHeight;
        double originalRatio = originalWidth / originalHeight;

        if (fitRatio > originalRatio) {
            //Die Weite muss zur Weite der Komponente passen. Oben & Unten abschneiden
            double widthFactor = toFitWidth / originalWidth;
            return new Dimension2D(toFitWidth, originalHeight * widthFactor);
        } else {
            //Die Höhe muss zur Höhe der Komponente passen. Links & Rechts abschneiden
            double heightFactor = toFitHeight / originalHeight;
            return new Dimension2D(originalWidth * heightFactor, toFitHeight);
        }
    }

    public static Dimension2D shouldFitIn(Dimension2D originalDim, double toFitWidth, double toFitHeigth) {
        return shouldFitIn(originalDim.getWidth(), originalDim.getHeight(), toFitWidth, toFitHeigth);
    }

    public static Dimension2D shouldFitIn(double originalWidth, double originalHeigth, Dimension2D toFitDim) {
        return shouldFitIn(originalWidth, originalHeigth, toFitDim.getWidth(), toFitDim.getHeight());
    }

    public static Dimension2D shouldFitIn(Dimension2D originalDim, Dimension2D toFitDim) {
        return shouldFitIn(originalDim.getWidth(), originalDim.getHeight(), toFitDim.getWidth(), toFitDim.getHeight());
    }

    public void relocate(Node node, double x, double y, double z) {
        node.setLayoutX(x - node.getLayoutBounds().getMinX());
        node.setLayoutY(y - node.getLayoutBounds().getMinY());
    }
}
