package com.guigarage.window.skin;

import com.guigarage.window.WindowBar;
import com.guigarage.window.WindowBarButton;
import com.guigarage.ui.WindowUtilities;
import com.guigarage.window.WindowStylerUtilities;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.input.MouseEvent;

public class WindowBarSkin extends SkinBase<WindowBar> {

    private EventHandler<MouseEvent> windowDragHandler;

    private Label titleLabel;

    private WindowBarButton closeButton;

    private WindowBarButton fullscreenButton;

    public WindowBarSkin(WindowBar control) {
        super(control);

        titleLabel = new Label();
        titleLabel.textProperty().bind(getSkinnable().titleProperty());
        titleLabel.getStyleClass().add("window-title");
        getChildren().add(titleLabel);

        closeButton = WindowStylerUtilities.createCloseButton(getSkinnable());
        fullscreenButton = WindowStylerUtilities.createFullscreenButton(getSkinnable());
        getChildren().addAll(closeButton, fullscreenButton);

        windowDragHandler = WindowStylerUtilities.createWindowDragHandler(getSkinnable());
        getSkinnable().addEventHandler(MouseEvent.ANY, windowDragHandler);
    }

    @Override
    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
        super.layoutChildren(contentX, contentY, contentWidth, contentHeight);

        titleLabel.resize(titleLabel.prefWidth(-1), titleLabel.prefHeight(-1));
        titleLabel.relocate((contentWidth - titleLabel.getWidth()) / 2 + contentX, (contentHeight - titleLabel.getHeight()) / 2 + contentY);

        closeButton.resize(closeButton.prefWidth(-1), closeButton.prefHeight(-1));
        closeButton.relocate(contentWidth - closeButton.getWidth() + contentX, contentY);

        fullscreenButton.resize(fullscreenButton.prefWidth(-1), fullscreenButton.prefHeight(-1));
        fullscreenButton.relocate(contentWidth - 6 - closeButton.getWidth() - fullscreenButton.getWidth() + contentX, contentY);
    }

    protected Label getTitleLabel() {
        return titleLabel;
    }

    protected WindowBarButton getCloseButton() {
        return closeButton;
    }

    protected WindowBarButton getFullscreenButton() {
        return fullscreenButton;
    }

    @Override
    public void dispose() {
        getSkinnable().removeEventHandler(MouseEvent.ANY, windowDragHandler);
    }
}
