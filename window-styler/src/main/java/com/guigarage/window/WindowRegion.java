package com.guigarage.window;

import com.guigarage.window.skin.WindowRegionSkin;
import com.guigarage.window.util.WindowResizeType;
import com.guigarage.ui.WindowUtilities;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.layout.Region;

public class WindowRegion extends Control {

    private ReadOnlyBooleanWrapper windowFullscreen;

    private ReadOnlyBooleanWrapper windowFocused;

    private WindowBar windowBar;

    private WindowResizeArea rightWindowResizeArea;

    private WindowResizeArea bottomWindowResizeArea;

    private WindowResizeArea cornerWindowResizeArea;

    private ObjectProperty<Region> content;

    public WindowRegion() {
        this(new WindowBar());
    }

    public WindowRegion(WindowBar windowBar) {
        getStyleClass().add("window-region");
        this.windowBar = windowBar;
        rightWindowResizeArea = new WindowResizeArea(WindowResizeType.HORIZONTAL);
        bottomWindowResizeArea = new WindowResizeArea(WindowResizeType.VERTICAL);
        cornerWindowResizeArea = new WindowResizeArea(WindowResizeType.BOTH);
        content = new SimpleObjectProperty<>();
        content.addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                getChildren().remove(oldValue);
            }
            if (newValue != null) {
                getChildren().add(newValue);
            }
            rightWindowResizeArea.toFront();
            bottomWindowResizeArea.toFront();
            cornerWindowResizeArea.toFront();
            windowBar.toFront();
        });
        getChildren().addAll(windowBar, rightWindowResizeArea, bottomWindowResizeArea, cornerWindowResizeArea);
        rightWindowResizeArea.toFront();
        bottomWindowResizeArea.toFront();
        cornerWindowResizeArea.toFront();
        windowBar.toFront();

        windowFocused = new ReadOnlyBooleanWrapper() {
            @Override
            protected void invalidated() {
                pseudoClassStateChanged(WindowStylerUtilities.WINDOW_FOCUSED_PSEUDO_CLASS_STATE, get());
            }
        };

        WindowUtilities.registerListenerForWindowFocus(this, e -> windowFocused.set(getScene().getWindow().isFocused()));

        windowFullscreen = new ReadOnlyBooleanWrapper() {
            @Override
            protected void invalidated() {
                pseudoClassStateChanged(WindowStylerUtilities.WINDOW_FULLSCREEN_PSEUDO_CLASS_STATE, get());
            }
        };

        WindowUtilities.registerListenerForStageFullscreen(this, e -> windowFullscreen.set(WindowUtilities.isInFullscreenMode(this)));
    }

    public Region getContent() {
        return content.get();
    }

    public void setContent(Region content) {
        this.content.set(content);
    }

    public ObjectProperty<Region> contentProperty() {
        return content;
    }

    public boolean isWindowFullscreen() {
        return windowFullscreen.get();
    }

    public boolean isWindowFocused() {
        return windowFocused.get();
    }

    public ReadOnlyBooleanProperty windowFocusedProperty() {
        return windowFocused.getReadOnlyProperty();
    }

    public ReadOnlyBooleanProperty windowFullscreenProperty() {
        return windowFullscreen.getReadOnlyProperty();
    }

    @Override
    public String getUserAgentStylesheet() {
        return WindowRegion.class.getResource("skin.css").toExternalForm();
    }

    @Override
    protected void layoutChildren() {
        windowBar.relocate(0, 0);
        windowBar.resize(getWidth(), windowBar.prefHeight(-1));
        rightWindowResizeArea.relocate(getWidth() - 6, 0);
        rightWindowResizeArea.resize(6, getHeight() - 6);
        bottomWindowResizeArea.relocate(0, getHeight() - 6);
        bottomWindowResizeArea.resize(getWidth() - 6, 6);
        cornerWindowResizeArea.relocate(getWidth() - 6, getHeight() - 6);
        cornerWindowResizeArea.resize(6, 6);
        if (content.get() != null) {
            content.get().resize(getWidth() - getPadding().getLeft() - getPadding().getRight(), getHeight() - getPadding().getTop() - getPadding().getBottom() - windowBar.getHeight());
            content.get().relocate(getPadding().getLeft(), windowBar.getHeight() + getPadding().getTop());
        }
    }

    @Override
    protected double computePrefHeight(double width) {
        double barHeight = windowBar.prefHeight(width);
        double contentHeight = 0;
        if(content.get() != null) {
            contentHeight = content.get().prefHeight(width);
        }
        return barHeight + getPadding().getTop() + contentHeight + getPadding().getBottom();
    }

    @Override
    protected double computeMinHeight(double width) {
        double barHeight = windowBar.minHeight(width);
        double contentHeight = 0;
        if(content.get() != null) {
            contentHeight = content.get().minHeight(width);
        }
        return barHeight + getPadding().getTop() + contentHeight + getPadding().getBottom();
    }

    @Override
    protected double computeMaxHeight(double width) {
        double barHeight = windowBar.maxHeight(width);
        double contentHeight = 0;
        if(content.get() != null) {
            contentHeight = content.get().maxHeight(width);
        }
        return barHeight + getPadding().getTop() + contentHeight + getPadding().getBottom();
    }

    @Override
    protected double computePrefWidth(double height) {
        double barWidth = windowBar.prefWidth(height);
        double contentWidth = 0;
        if(content.get() != null) {
            contentWidth = content.get().prefWidth(height);
        }
        return Math.max(barWidth, getPadding().getLeft() + contentWidth + getPadding().getRight());
    }

    @Override
    protected double computeMinWidth(double height) {
        double barWidth = windowBar.minWidth(height);
        double contentWidth = 0;
        if(content.get() != null) {
            contentWidth = content.get().minWidth(height);
        }
        return Math.max(barWidth, getPadding().getLeft() + contentWidth + getPadding().getRight());
    }

    @Override
    protected double computeMaxWidth(double height) {
        double barWidth = windowBar.maxWidth(height);
        if(content.get() != null) {
            double contentWidth = content.get().maxWidth(height);
            return Math.min(barWidth, getPadding().getLeft() + contentWidth + getPadding().getRight());
        }
        return barWidth;
    }

    public WindowBar getWindowBar() {
        return windowBar;
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new WindowRegionSkin(this);
    }
}
