package com.guigarage.material;

import javafx.animation.Transition;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Optional;

public class MaterialToolbar extends Region {

    private AnimatedIcon mainIcon;

    private Circle changeViewAnimationCircle;

    private Label currentTitle;

    private Label changeViewAnimationTitle;

    private Rectangle backgroundClip;

    private Rectangle background;

    private HBox actionBox;

    public MaterialToolbar() {
        getStyleClass().add("material-toolbar");
        mainIcon = new AnimatedIcon();
        mainIcon.setIconFill(Color.WHITE);

        changeViewAnimationCircle = new Circle();
        changeViewAnimationCircle.setOpacity(0);
        changeViewAnimationCircle.setManaged(false);

        currentTitle = new Label("Main");
        currentTitle.getStyleClass().add("material-toolbar-text");

        changeViewAnimationTitle = new Label();
        changeViewAnimationTitle.setOpacity(0);
        changeViewAnimationTitle.getStyleClass().add("material-toolbar-text");

        background = new Rectangle();
        background.setManaged(false);
        background.setFill(Color.DARKSLATEBLUE);

        backgroundClip = new Rectangle();
        backgroundClip.setFill(Color.BLACK);
        backgroundClip.xProperty().bind(background.xProperty());
        backgroundClip.yProperty().bind(background.yProperty());
        backgroundClip.widthProperty().bind(background.widthProperty());
        backgroundClip.heightProperty().bind(background.heightProperty());
        changeViewAnimationCircle.setClip(backgroundClip);

        actionBox = new HBox();
        actionBox.setSpacing(6);

        getChildren().addAll(mainIcon, changeViewAnimationCircle, currentTitle, changeViewAnimationTitle, background, actionBox);

        background.toBack();
        changeViewAnimationCircle.toFront();
        changeViewAnimationTitle.toFront();
        currentTitle.toFront();
        mainIcon.toFront();
        actionBox.toFront();
    }

    public void animateTo(Color color, String title, IconType iconType, ToolbarAction... actions) {
        changeViewAnimationCircle.setOpacity(0);
        changeViewAnimationCircle.setFill(color);

        changeViewAnimationTitle.setOpacity(0);
        changeViewAnimationTitle.setText(title);

        Transition circleTransition = new Transition() {

            {
                setCycleDuration(Duration.millis(480));
            }

            @Override
            protected void interpolate(double frac) {
                changeViewAnimationCircle.setRadius((getWidth() + 32) * frac);
                changeViewAnimationCircle.setOpacity(0.2 + frac);

                currentTitle.setTranslateY(-currentTitle.getLayoutBounds().getHeight() * frac);
                currentTitle.setOpacity(1 - frac * 1.5);

                changeViewAnimationTitle.setTranslateY(currentTitle.getLayoutBounds().getHeight() - currentTitle.getLayoutBounds().getHeight() * frac);
                changeViewAnimationTitle.setOpacity(frac);

                for(Node n : actionBox.getChildren()) {
                    n.setScaleX(1 - frac);
                    n.setScaleY(1 - frac);
                    n.setOpacity(1 - frac);
                }
            }
        };

        circleTransition.setOnFinished(e -> {
            background.setFill(color);
            changeViewAnimationCircle.setOpacity(0);
            currentTitle.setTranslateY(0);
            currentTitle.setText(title);
            currentTitle.setOpacity(1);
            changeViewAnimationTitle.setOpacity(0);
            actionBox.getChildren().clear();
            actionBox.setOpacity(0);

            for(ToolbarAction action : actions) {
                Button actionButton = new Button(action.getIconText());
                actionButton.setOnAction(action.getOnAction());
                actionButton.getStyleClass().add("material-toolbar-action");
                actionBox.getChildren().add(actionButton);
            }

            Transition actionTransition = new Transition() {

                {
                    setCycleDuration(Duration.millis(360));
                }

                @Override
                protected void interpolate(double frac) {
                    for(Node n : actionBox.getChildren()) {
                        n.setScaleX(frac);
                        n.setScaleY(frac);
                    }
                    actionBox.setOpacity(frac);
                }
            };
            actionTransition.play();

        });

        if (iconType.equals(IconType.ARROW)) {
            mainIcon.toArrow();
        } else if (iconType.equals(IconType.BACK)) {
            mainIcon.toBack();
        } else if (iconType.equals(IconType.CLOSE)) {
            mainIcon.toClose();
        } else if (iconType.equals(IconType.MENU)) {
            mainIcon.toMenu();
        } else if (iconType.equals(IconType.PAUSE)) {
            mainIcon.toPause();
        } else if (iconType.equals(IconType.PLAY)) {
            mainIcon.toPlay();
        }

        circleTransition.play();
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();

        mainIcon.relocate(getPadding().getLeft(), getPadding().getTop());
        mainIcon.resize(mainIcon.prefWidth(-1), mainIcon.prefHeight(-1));

        double titleX = mainIcon.getLayoutX() + mainIcon.getWidth() + 12;
        double titleY = mainIcon.getLayoutY() + (mainIcon.getHeight() - currentTitle.prefHeight(-1)) / 2;
        double titleWidth = getWidth() - titleX * 2;
        double titleHeight = currentTitle.prefHeight(-1);

        currentTitle.relocate(titleX, titleY);
        currentTitle.resize(titleWidth, titleHeight);

        actionBox.relocate(getWidth() - getPadding().getRight() - actionBox.prefWidth(-1), getPadding().getTop());
        actionBox.resize(actionBox.prefWidth(-1), actionBox.prefHeight(-1));

        changeViewAnimationTitle.relocate(titleX, titleY);
        changeViewAnimationTitle.resize(titleWidth, titleHeight);

        Insets borderInsets = Optional.ofNullable(getBorder()).map(border -> border.getInsets()).orElse(Insets.EMPTY);
        background.setX(borderInsets.getLeft());
        background.setY(borderInsets.getTop());
        background.setWidth(getWidth() - borderInsets.getLeft() - borderInsets.getRight());
        background.setHeight(getHeight() - borderInsets.getTop() - borderInsets.getBottom());

        changeViewAnimationCircle.setCenterX(mainIcon.getLayoutX() + mainIcon.getWidth() / 2);
        changeViewAnimationCircle.setCenterY(mainIcon.getLayoutY() + mainIcon.getHeight() / 2);
    }
}
