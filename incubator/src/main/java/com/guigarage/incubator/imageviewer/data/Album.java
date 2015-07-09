package com.guigarage.incubator.imageviewer.data;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Album {

    private StringProperty name;

    private ObservableList<ImageObject> images;

    private ObjectProperty<Character> icon;

    public Album(ImageObject... images) {
        this.images = FXCollections.observableArrayList(images);
        name = new SimpleStringProperty();
        icon = new SimpleObjectProperty<>('\uf07b');
    }

    public String getName() {
        return nameProperty().get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        nameProperty().set(name);
    }

    public Character getIcon() {
        return iconProperty().get();
    }

    public ObjectProperty<Character> iconProperty() {
        return icon;
    }

    public void setIcon(Character icon) {
        iconProperty().set(icon);
    }

    public ObservableList<ImageObject> getImages() {
        return images;
    }
}
