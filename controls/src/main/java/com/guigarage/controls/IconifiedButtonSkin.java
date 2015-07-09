package com.guigarage.controls;

import com.guigarage.css.CssHelper;
import com.guigarage.css.SkinPropertyBasedCssMetaData;
import com.guigarage.ui.IconFonts;
import com.sun.javafx.css.converters.StringConverter;
import com.sun.javafx.scene.control.skin.ButtonSkin;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.util.List;

public class IconifiedButtonSkin extends ButtonSkin {

    private StyleableObjectProperty<String> iconText;

    public IconifiedButtonSkin(Button button) {
        super(button);
        getSkinnable().textProperty().bind(iconTextProperty());
    }

    public String getIconText() {
        return iconText == null ? "" : iconText.get();
    }

    public void setIconText(String iconText) {
        this.iconText.set(iconText);
    }

    public StyleableObjectProperty<String> iconTextProperty() {
        if (iconText == null) {
            iconText = CssHelper.createProperty(StyleableProperties.ICON_TEXT, this);
        }
        return iconText;
    }

    private static class StyleableProperties {
        private static final SkinPropertyBasedCssMetaData<Button, String> ICON_TEXT = CssHelper.createSkinMetaData("-fx-icon-text", StringConverter.getInstance(), "iconText", "");
        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES = CssHelper.createCssMetaDataList(ButtonSkin.getClassCssMetaData(), ICON_TEXT);
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return StyleableProperties.STYLEABLES;
    }

    public static void addStylesheet(Scene scene) {
        IconFonts.addToScene(scene);
        scene.getStylesheets().add(IconifiedButtonSkin.class.getResource("iconified-button-skin.css").toExternalForm());
    }

    public static void addStylesheet(Parent parent) {
        IconFonts.addToParent(parent);
        parent.getStylesheets().add(IconifiedButtonSkin.class.getResource("iconified-button-skin.css").toExternalForm());
    }

    public static void addStyle(Button button) {
        addStylesheet(button);
        addStyleClass(button);
    }

    public static void addStyleClass(Button button) {
        button.getStyleClass().add("iconified-button");
    }
}
