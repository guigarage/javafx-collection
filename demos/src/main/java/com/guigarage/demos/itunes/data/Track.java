package com.guigarage.demos.itunes.data;

public class Track {

    private Album album;

    private String name;

    private int number;

    private String previewUrl;

    public Track(Album album, String name, int number, String previewUrl) {
        this.album = album;
        this.name = name;
        this.number = number;
        this.previewUrl = previewUrl;
    }

    public Album getAlbum() {
        return album;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }
}
