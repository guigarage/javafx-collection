package com.guigarage.window;

import com.sun.javafx.Utils;
import javafx.css.PseudoClass;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by hendrikebbers on 07.10.14.
 */
public class WindowStylerUtilities {

    public static final PseudoClass WINDOW_FULLSCREEN_PSEUDO_CLASS_STATE = PseudoClass.getPseudoClass("window-fullscreen");

    public static final PseudoClass WINDOW_FOCUSED_PSEUDO_CLASS_STATE = PseudoClass.getPseudoClass("window-focused");

    public static Scene initForWindowStyler(Stage stage, WindowRegion region) {
        Scene scene = new Scene(region);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        return scene;
    }

    public static WindowBarButton createCloseButton(WindowBar windowBar) {
        WindowBarButton closeButton = new WindowBarButton('\uf00d');
        closeButton.setId("window-close-button");
        closeButton.setOnMouseClicked(e -> windowBar.closeWindow());
        return closeButton;
    }

    public static WindowBarButton createFullscreenButton(WindowBar windowBar) {
        WindowBarButton fullscreenButton = new WindowBarButton('\uf065');
        fullscreenButton.setId("window-fullscreen-button");
        fullscreenButton.setOnMouseClicked(e -> windowBar.setWindowFullscreen(!windowBar.isWindowFullscreen()));
        return fullscreenButton;
    }

    public static EventHandler<MouseEvent> createWindowDragHandler(Node nodeInWindow) {
        return new EventHandler<MouseEvent>() {

            private Point2D lastDragPosition;

            @Override
            public void handle(MouseEvent e) {
                if (e.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
                    if (nodeInWindow.getScene().getWindow() instanceof Stage) {
                        ((Stage) nodeInWindow.getScene().getWindow()).toFront();
                    }
                    nodeInWindow.getScene().getWindow().requestFocus();
                    lastDragPosition = new Point2D(e.getScreenX(), e.getScreenY());
                } else if (e.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
                    double minY = 0;
                    if (Utils.isMac()) {
                        minY = 22;
                    }
                    nodeInWindow.getScene().getWindow().setX(nodeInWindow.getScene().getWindow().getX() + e.getScreenX() - lastDragPosition.getX());
                    nodeInWindow.getScene().getWindow().setY(Math.max(minY, nodeInWindow.getScene().getWindow().getY() + e.getScreenY() - lastDragPosition.getY()));

                    lastDragPosition = new Point2D(e.getScreenX(), e.getScreenY());
                }
            }
        };
    }
}
