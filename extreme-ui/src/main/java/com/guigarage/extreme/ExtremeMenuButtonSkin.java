package com.guigarage.extreme;

import com.guigarage.css.CssHelper;
import com.guigarage.css.SkinPropertyBasedCssMetaData;
import com.guigarage.ui.UiUtilities;
import com.sun.javafx.css.converters.PaintConverter;
import com.sun.javafx.scene.control.behavior.ButtonBehavior;
import com.sun.javafx.scene.control.skin.LabeledSkinBase;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.scene.control.Control;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.List;

public class ExtremeMenuButtonSkin extends LabeledSkinBase<ExtremeMenuButton, ButtonBehavior<ExtremeMenuButton>> {

    private Circle buttonCircle;
    private Circle springCircle;
    private Rectangle textRectangle;
    private Text label;
    private Text iconText;
    private double textInsets = 4;
    private double gap = 6;
    private StyleableObjectProperty<Paint> backgroundFill;


    public ExtremeMenuButtonSkin(ExtremeMenuButton button) {
        this(button, Duration.millis(320));
    }

    public ExtremeMenuButtonSkin(ExtremeMenuButton button, Duration animationDuration) {
        super(button, new ButtonBehavior<ExtremeMenuButton>(button));
        getChildren().clear();

        getSkinnable().setMinHeight(Region.USE_PREF_SIZE);
        getSkinnable().setMinWidth(Region.USE_PREF_SIZE);
        getSkinnable().setMaxHeight(Region.USE_PREF_SIZE);
        getSkinnable().setMaxWidth(Region.USE_PREF_SIZE);

        buttonCircle = new Circle();
        springCircle = new Circle();
        springCircle.radiusProperty().bind(buttonCircle.radiusProperty());
        springCircle.centerXProperty().bind(buttonCircle.centerXProperty());
        springCircle.centerYProperty().bind(buttonCircle.centerYProperty());
        springCircle.fillProperty().bind(buttonCircle.fillProperty());
        springCircle.setVisible(false);

        ScaleTransition springCirlceScale = new ScaleTransition(animationDuration, springCircle);
        springCirlceScale.setFromX(1.0);
        springCirlceScale.setToX(2.0);
        springCirlceScale.setFromY(1.0);
        springCirlceScale.setToY(2.0);
        Transition springCirlceOpacity = new Transition() {

            {
                setCycleDuration(animationDuration);
            }

            @Override
            protected void interpolate(double frac) {
                springCircle.setOpacity(0.7 - frac * 0.7);
            }
        };
        ParallelTransition springCircleTransition = new ParallelTransition(springCirlceScale, springCirlceOpacity);
        springCircleTransition.setOnFinished((e) -> springCircle.setVisible(false));


        getSkinnable().pressedProperty().addListener((e) -> {
            if (getSkinnable().isPressed()) {
                springCircle.toFront();
                springCircle.setOpacity(0.7);
                springCircle.setVisible(true);
                springCircleTransition.playFromStart();
            }
        });


        textRectangle = new Rectangle();
        label = new Text();
        iconText = UiUtilities.createIconText(getSkinnable().iconProperty().get(), 64);
        iconText.textProperty().bind(getSkinnable().iconProperty().asString());
        iconText.setFill(Color.WHITE);
        iconText.setOpacity(0.8);

        textRectangle.setFill(((Color) getBackgroundFill()).brighter());
        textRectangle.visibleProperty().bind(getSkinnable().hoverProperty());

        label.textProperty().bind(button.textProperty());
        label.setFill(Color.WHITE);
        label.setTextAlignment(TextAlignment.CENTER);
        label.visibleProperty().bind(getSkinnable().hoverProperty());
        getChildren().addAll(buttonCircle, textRectangle, label, iconText, springCircle);
        iconText.toFront();

        getSkinnable().hoverProperty().addListener((e) -> updateColors());
        backgroundFillProperty().addListener((e) -> updateColors());
        updateColors();

        button.fontSizePropertyProperty().addListener(e -> {
            label.setFont(UiUtilities.create(label.getFont(), button.getFontSize()));
            iconText.setFont(UiUtilities.create(iconText.getFont(), button.getFontSize() * 4));
        });
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StyleableProperties.STYLEABLES;
    }

    private void updateColors() {
        Color c = (Color) getBackgroundFill();
        textRectangle.setFill(c.deriveColor(1.0, 1.0, 1.0, 0.4));
        if (getSkinnable().isHover()) {
            buttonCircle.setFill(c);
            textRectangle.setFill(c.deriveColor(1.0, 1.0, 1.0, 0.4));
        } else {
            buttonCircle.setFill(c.deriveColor(1.0, 1.0, 1.0, 0.4));
            textRectangle.setFill(c.deriveColor(1.0, 1.0, 1.0, 0.1));
        }
    }

    public Paint getBackgroundFill() {
        return backgroundFill == null ? Color.RED : backgroundFill.get();
    }

    public void setBackgroundFill(Paint backgroundFill) {
        this.backgroundFill.set(backgroundFill);
    }

    public StyleableObjectProperty<Paint> backgroundFillProperty() {
        if (backgroundFill == null) {
            backgroundFill = CssHelper.createProperty(StyleableProperties.BACKGROUND_FILL, this);
        }
        return backgroundFill;
    }

    @Override
    protected void layoutChildren(double x, double y, double w, double h) {
        super.layoutChildren(x, y, w, h);
        textRectangle.setHeight(label.getLayoutBounds().getHeight() + textInsets * 2);
        textRectangle.setWidth(w);
        textRectangle.relocate(x, h - textRectangle.getHeight());
        buttonCircle.setRadius(textRectangle.getWidth() / 2);
        buttonCircle.setCenterX(x + w / 2);
        buttonCircle.setCenterY(y + w / 2);

        iconText.relocate((x + w / 2) - iconText.getLayoutBounds().getWidth() / 2, (y + buttonCircle.getRadius()) - iconText.getLayoutBounds().getHeight() / 2);

        label.relocate(x + (w - label.getLayoutBounds().getWidth()) / 2, h - textRectangle.getHeight() + textInsets);
    }

    @Override
    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        double circleWidth = computePrefWidth(0, topInset, rightInset, bottomInset, leftInset);

        return (label.getLayoutBounds().getHeight() + textInsets * 2) + gap + circleWidth + topInset + bottomInset;
    }

    @Override
    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return Math.max(label.getLayoutBounds().getWidth(), label.getLayoutBounds().getHeight() * 6) + textInsets * 2 + rightInset + leftInset;
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return getClassCssMetaData();    //To change body of overridden methods use File | Settings | File Templates.
    }

    private static class StyleableProperties {
        private static final SkinPropertyBasedCssMetaData<ExtremeMenuButton, Paint> BACKGROUND_FILL = CssHelper.createSkinMetaData("-fx-background-fill", PaintConverter.getInstance(), "backgroundFill", Color.RED);
        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES = CssHelper.createCssMetaDataList(Control.getClassCssMetaData(), BACKGROUND_FILL);
    }
}
