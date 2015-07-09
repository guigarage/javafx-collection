package com.guigarage.window;

import com.guigarage.window.skin.WindowBarSkin;
import com.guigarage.ui.WindowUtilities;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.stage.Stage;

public class WindowBar extends Control {

    private ReadOnlyBooleanWrapper windowFullscreen;

    private ReadOnlyBooleanWrapper windowFocused;

    private StringProperty title;

    public WindowBar() {
        getStyleClass().add("window-bar");

        this.title = new SimpleStringProperty();
        WindowUtilities.bindToStageTitle(this, this.title);

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

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    @Override
    public String getUserAgentStylesheet() {
        return WindowBar.class.getResource("skin.css").toExternalForm();
    }

    public boolean isWindowFullscreen() {
        return windowFullscreen.get();
    }

    public void setWindowFullscreen(boolean fullscreen) {
        if (getScene().getWindow() instanceof Stage) {
            ((Stage) getScene().getWindow()).setFullScreen(fullscreen);
        }
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

    public void closeWindow() {
        getScene().getWindow().hide();
    }

    public void setWindowMaximized(boolean maximize) {
        if (getScene().getWindow() instanceof Stage) {
            ((Stage) getScene().getWindow()).setMaximized(maximize);
        }
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new WindowBarSkin(this);
    }
}
