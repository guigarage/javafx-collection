package com.guigarage.window;

import com.guigarage.ui.UiUtilities;
import com.guigarage.window.skin.WindowBarButtonSkin;
import com.guigarage.ui.WindowUtilities;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.text.Text;

public class WindowBarButton extends Control {

    private ObjectProperty<Node> graphic;

    private ReadOnlyBooleanWrapper windowFullscreen;

    private ReadOnlyBooleanWrapper windowFocused;

    public WindowBarButton() {
        getStyleClass().add("window-bar-button");
        graphic = new SimpleObjectProperty<>();

        windowFocused = new ReadOnlyBooleanWrapper() {
            @Override
            protected void invalidated() {
                pseudoClassStateChanged(WindowStylerUtilities.WINDOW_FOCUSED_PSEUDO_CLASS_STATE, get());
            }
        };

        WindowUtilities.registerListenerForWindowFocus(this, e -> windowFocused.set(getScene().getWindow().isFocused()));
        if(getScene() != null && getScene().getWindow() != null) {
            pseudoClassStateChanged(WindowStylerUtilities.WINDOW_FOCUSED_PSEUDO_CLASS_STATE, getScene().getWindow().isFocused());
        }

        windowFullscreen = new ReadOnlyBooleanWrapper() {
            @Override
            protected void invalidated() {
                pseudoClassStateChanged(WindowStylerUtilities.WINDOW_FULLSCREEN_PSEUDO_CLASS_STATE, get());
            }
        };
        WindowUtilities.registerListenerForStageFullscreen(this, e -> windowFullscreen.set(WindowUtilities.isInFullscreenMode(this)));
    }

    public WindowBarButton(Character iconChar) {
        this();
        Text icon = UiUtilities.createIconText(iconChar, 14);
        icon.getStyleClass().add("window-bar-button-icon");
        graphic.setValue(icon);
    }

    public Node getGraphic() {
        return graphic.get();
    }

    public ObjectProperty<Node> graphicProperty() {
        return graphic;
    }

    public void setGraphic(Node graphic) {
        this.graphic.set(graphic);
    }

    @Override
    public String getUserAgentStylesheet() {
        return WindowBarButton.class.getResource("skin.css").toExternalForm();
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new WindowBarButtonSkin(this);
    }
}
