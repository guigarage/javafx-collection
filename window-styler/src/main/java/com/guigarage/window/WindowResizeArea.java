package com.guigarage.window;

import com.guigarage.window.util.WindowResizeType;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class WindowResizeArea extends Region {

    private double dragOffsetX, dragOffsetY;

    private WindowResizeType resizeType;

    public WindowResizeArea(WindowResizeType resizeType) {
        setStyle("-fx-background-color: transparent;");
        this.resizeType = resizeType;
        if (resizeType.equals(WindowResizeType.HORIZONTAL)) {
            setCursor(Cursor.H_RESIZE);
        } else if (resizeType.equals(WindowResizeType.VERTICAL)) {
            setCursor(Cursor.V_RESIZE);
        } else if (resizeType.equals(WindowResizeType.BOTH)) {
            setCursor(Cursor.SE_RESIZE);
        } else {
            setCursor(null);
        }

        addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            final double stageY = getScene().getWindow().getY();
            dragOffsetX = (getScene().getWindow().getX() + getScene().getWindow().getWidth()) - e.getScreenX();
            dragOffsetY = (stageY + getScene().getWindow().getHeight()) - e.getScreenY();
        });
        addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            final double stageY = getScene().getWindow().getY(); // TODO Workaround for RT-13980
            final Screen screen = Screen.getScreensForRectangle(getScene().getWindow().getX(), stageY, 1, 1).get(0);
            Rectangle2D visualBounds = screen.getVisualBounds();
            double maxX = Math.min(visualBounds.getMaxX(), e.getScreenX() + dragOffsetX);
            double maxY = Math.min(visualBounds.getMaxY(), e.getScreenY() - dragOffsetY);
            if (resizeType.equals(WindowResizeType.HORIZONTAL) || resizeType.equals(WindowResizeType.BOTH)) {
                double width = maxX - getScene().getWindow().getX();
                double minWidth = getScene().getRoot().minWidth(-1);
                double maxWidth = getScene().getRoot().maxWidth(-1);
                if (getScene().getWindow() instanceof Stage) {
                    minWidth = Math.max(((Stage) getScene().getWindow()).getMinWidth(), minWidth);
                    maxWidth = Math.min(((Stage) getScene().getWindow()).getMaxWidth(), maxWidth);
                }
                width = Math.min(maxWidth, Math.max(minWidth, width));
                getScene().getWindow().setWidth(width);
            }
            if (resizeType.equals(WindowResizeType.VERTICAL) || resizeType.equals(WindowResizeType.BOTH)) {
                double height = maxY - stageY;
                double minHeight = getScene().getRoot().minHeight(-1);
                double maxHeight = getScene().getRoot().maxHeight(-1);
                if (getScene().getWindow() instanceof Stage) {
                    minHeight = Math.max(((Stage) getScene().getWindow()).getMinHeight(), minHeight);
                    maxHeight = Math.min(((Stage) getScene().getWindow()).getMaxHeight(), maxHeight);
                }
                height = Math.min(maxHeight, Math.max(minHeight, height));
                getScene().getWindow().setHeight(height);
            }
        });
    }
}