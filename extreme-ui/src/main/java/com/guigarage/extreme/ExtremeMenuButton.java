package com.guigarage.extreme;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Skin;

public class ExtremeMenuButton extends Button {

    private ObjectProperty<Character> icon;

    private DoubleProperty fontSizeProperty;

    public ExtremeMenuButton(String title, Character icon) {
        super(title);
        getStyleClass().clear();
        this.icon = new SimpleObjectProperty<>(icon);
        fontSizeProperty = new SimpleDoubleProperty(16);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new ExtremeMenuButtonSkin(this);
    }

    public double getFontSize() {
        return fontSizeProperty.get();
    }

    public DoubleProperty fontSizePropertyProperty() {
        return fontSizeProperty;
    }

    public void setFontSize(double fontSizeProperty) {
        this.fontSizeProperty.set(fontSizeProperty);
    }

    public Character getIcon() {
        return icon.get();
    }

    public ObjectProperty<Character> iconProperty() {
        return icon;
    }
}
