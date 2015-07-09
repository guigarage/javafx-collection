package com.guigarage.extreme;

import com.guigarage.ui.UiUtilities;
import javafx.animation.Transition;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class BasicExtremeMenu extends Region {

    private Text showMenuIcon;
    private Side side;

    public BasicExtremeMenu() {
        this(Side.BOTTOM);
    }

    public BasicExtremeMenu(Side side) {
        this.side = side;

        if (side == Side.BOTTOM) {
            setStyle("-fx-background-color: rgba(255, 255, 255, 0.7);" +
                    "-fx-border-width: 1 0 0 0;" +
                    "-fx-border-color: gray;");
        } else {
            setStyle("-fx-background-color: rgba(255, 255, 255, 0.7);" +
                    "-fx-border-width: 0 0 1 0;" +
                    "-fx-border-color: gray;");
        }
        if (side == Side.BOTTOM) {
            showMenuIcon = UiUtilities.createIconText('\uf062', 22);
        } else {
            showMenuIcon = UiUtilities.createIconText('\uf063', 22);
        }
        showMenuIcon.setFill(Color.rgb(100, 100, 100, 0.5));
        getChildren().add(showMenuIcon);

        Transition hideMenuTransition = new Transition() {

            {
                setCycleDuration(Duration.millis(440));
            }

            @Override
            protected void interpolate(double frac) {
                if(side == Side.BOTTOM) {
                    setTranslateY((getHeight() - (2 + showMenuIcon.getLayoutBounds().getHeight() + 2)) * frac);
                } else {
                    setTranslateY(-(getHeight() - (2 + showMenuIcon.getLayoutBounds().getHeight() + 2)) * frac);
                }
            }
        };

        Transition hideItemsTransition = new Transition() {

            {
                setCycleDuration(Duration.millis(440));
            }

            @Override
            protected void interpolate(double frac) {
                for (Node child : getChildren()) {
                    if (!child.equals(showMenuIcon)) {
                        child.setOpacity(1.0 - frac);
                    }
                }
                showMenuIcon.setOpacity(frac);
            }
        };

        Transition showMenuTransition = new Transition() {

            {
                setCycleDuration(Duration.millis(440));
            }

            @Override
            protected void interpolate(double frac) {
                if(side == Side.BOTTOM) {
                    setTranslateY((getHeight() - (2 + showMenuIcon.getLayoutBounds().getHeight() + 2)) * (1 - frac));
                } else {
                    setTranslateY(-(getHeight() - (2 + showMenuIcon.getLayoutBounds().getHeight() + 2)) * (1 - frac));
                }
            }
        };

        Transition showItemsTransition = new Transition() {

            {
                setCycleDuration(Duration.millis(440));
            }

            @Override
            protected void interpolate(double frac) {
                for (Node child : getChildren()) {
                    if (!child.equals(showMenuIcon)) {
                        child.setOpacity(frac);
                    }
                }
                showMenuIcon.setOpacity(1.0 - frac);
            }
        };

        hoverProperty().addListener(e -> {
            if (isHover()) {
                showMenuTransition.playFromStart();
                showItemsTransition.playFromStart();
            } else {
                hideMenuTransition.playFromStart();
                hideItemsTransition.playFromStart();
            }
        });
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        if (side == Side.BOTTOM) {
            showMenuIcon.relocate((getWidth() - showMenuIcon.getLayoutBounds().getWidth()) / 2, 2);
        } else {
            showMenuIcon.relocate((getWidth() - showMenuIcon.getLayoutBounds().getWidth()) / 2, getHeight() - 2 - showMenuIcon.getLayoutBounds().getHeight());
        }
    }

    public void setArrowFill(Color color) {
        showMenuIcon.setFill(color);
    }
}

