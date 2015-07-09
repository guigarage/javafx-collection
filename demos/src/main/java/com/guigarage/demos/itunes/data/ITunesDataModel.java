package com.guigarage.demos.itunes.data;

import io.datafx.controller.injection.scopes.FlowScoped;
import io.datafx.io.RestSource;
import io.datafx.io.converter.JsonConverter;
import io.datafx.provider.ListDataProvider;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;

@FlowScoped
public class ITunesDataModel {

    private ObservableList<Album> albums;

    private Album selectedAlbum;

    private Track selectedTrack;

    public ITunesDataModel() {
        albums = FXCollections.<Album>observableArrayList();
    }

    public Album getSelectedAlbum() {
        return selectedAlbum;
    }

    public void setSelectedAlbum(Album selectedAlbum) {
        this.selectedAlbum = selectedAlbum;
    }

    public Track getSelectedTrack() {
        return selectedTrack;
    }

    public void setSelectedTrack(Track selectedTrack) {
        this.selectedTrack = selectedTrack;
    }

    public ObservableList<Album> getAlbums() {
        return albums;
    }

    public void updateBySearch(String term) {
        RestSource<ITunesMedia> source = new RestSource<>("http://itunes.apple.com", new JsonConverter<ITunesMedia>("results", ITunesMedia.class));
        source.setPath("search");
        Map<String, String> params = new HashMap<>();
        params.put("term", term.replace(" ", "+"));
        params.put("media", "music");
        source.setQueryParams(params);
        ListDataProvider<ITunesMedia> provider = new ListDataProvider<>(source);
        ObservableList<ITunesMedia> mediaList = FXCollections.<ITunesMedia>observableArrayList();
        provider.setResultObservableList(mediaList);
        provider.retrieve();

        albums.clear();

        mediaList.addListener((ListChangeListener<ITunesMedia>) c -> {
            while (c.next()) {
                for (ITunesMedia media : c.getAddedSubList()) {
                    if (media.getCollectionName() != null) {
                        Album album = getAlbumForName(media.getCollectionName());
                        if (album == null) {
                            album = new Album(media.getArtistName(), media.getCoverUrl(), media.getPrimaryGenreName(), media.getCollectionName());
                            albums.add(album);
                        }
                        album.addTrack(media.getTrackName(), media.getTrackNumber(), media.getPreviewUrl());
                    }
                }
            }
        });
    }

    private Album getAlbumForName(String name) {
        for (Album album : albums) {
            if (album.getName().equals(name)) {
                return album;
            }
        }
        return null;
    }
}
