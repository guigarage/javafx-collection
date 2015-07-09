package com.guigarage.window.skin;

import com.guigarage.window.WindowBarButton;
import javafx.scene.control.SkinBase;

public class WindowBarButtonSkin extends SkinBase<WindowBarButton> {

    public WindowBarButtonSkin(WindowBarButton control) {
        super(control);
        getSkinnable().graphicProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                getChildren().remove(oldValue);
            }
            if (newValue != null) {
                getChildren().add(newValue);
            }
        });
        if (getSkinnable().getGraphic() != null) {
            getChildren().add(getSkinnable().getGraphic());
        }
    }

    @Override
    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
        super.layoutChildren(contentX, contentY, contentWidth, contentHeight);
        if (getSkinnable().getGraphic() != null) {
            double iconTextWidth = getSkinnable().getGraphic().prefWidth(-1);
            double iconTextHeight = getSkinnable().getGraphic().prefHeight(-1);
            getSkinnable().getGraphic().resize(iconTextWidth, iconTextHeight);
            getSkinnable().getGraphic().relocate((contentWidth - iconTextWidth) / 2 + contentX, (contentHeight - iconTextHeight) / 2 + contentY);
        }
    }

    @Override
    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return topInset + getSkinnable().getGraphic().prefHeight(width) + bottomInset;
    }

    @Override
    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return leftInset + getSkinnable().getGraphic().prefWidth(height) + rightInset;
    }

}
