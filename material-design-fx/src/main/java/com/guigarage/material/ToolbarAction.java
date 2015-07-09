package com.guigarage.material;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ToolbarAction {

    private EventHandler<ActionEvent> onAction;

    private String iconText;

    public ToolbarAction(EventHandler<ActionEvent> onAction, String iconText) {
        this.onAction = onAction;
        this.iconText = iconText;
    }

    public EventHandler<ActionEvent> getOnAction() {
        return onAction;
    }

    public String getIconText() {
        return iconText;
    }
}
