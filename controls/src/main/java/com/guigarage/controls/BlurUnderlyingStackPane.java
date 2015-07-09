package com.guigarage.controls;

import javafx.beans.InvalidationListener;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;

public class BlurUnderlyingStackPane extends StackPane {

    private EventHandler blockingEventHandler;

    public BlurUnderlyingStackPane() {
        blockingEventHandler = e -> e.consume();

        getChildren().addListener((InvalidationListener)e -> {
            if(!getChildren().isEmpty()) {
                for (Node child : getChildren()) {
                    if (child != getChildren().get(getChildren().size() - 1)) {
                        child.setEffect(new GaussianBlur());
                        child.addEventFilter(EventType.ROOT, blockingEventHandler);
                    } else {
                        child.removeEventFilter(EventType.ROOT, blockingEventHandler);
                        child.setEffect(null);
                        child.requestFocus();
                    }
                }
            }
        });
    }
}
