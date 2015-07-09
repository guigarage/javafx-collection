package com.guigarage.demos.itunes.data;

import com.guigarage.controls.Media;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

public class Album implements Media {

    private String artist;

    private String coverUrl;

    private String genre;

    private String name;

    private ObservableList<Track> tracks;

    public Album(String artist, String coverUrl, String genre, String name) {
        this.artist = artist;
        this.coverUrl = coverUrl;
        this.genre = genre;
        this.name = name;
        tracks = FXCollections.observableArrayList();
    }

    public void addTrack(String name, int number, String previewUrl) {
        tracks.add(new Track(this, name, number, previewUrl));
    }

    public String getArtist() {
        return artist;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public String getGenre() {
        return genre;
    }

    public String getName() {
        return name;
    }

    public ObservableList<Track> getTracks() {
        return tracks;
    }

    @Override
    public StringProperty titleProperty() {
        return new SimpleStringProperty(getName());
    }

    @Override
    public StringProperty descriptionProperty() {
        return new SimpleStringProperty(getArtist());
    }

    @Override
    public ObjectProperty<Image> imageProperty() {
        return new SimpleObjectProperty<>(new Image(getCoverUrl(), true));
    }

    @Override
    public String toString() {
        return getArtist() + " - " +  getName();
    }
}
