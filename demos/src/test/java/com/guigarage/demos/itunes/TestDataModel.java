package com.guigarage.demos.itunes;

import com.guigarage.demos.itunes.data.Album;
import com.guigarage.demos.itunes.data.ITunesDataModel;
import com.guigarage.ui.BasicUtils;

public class TestDataModel extends ITunesDataModel {

    @Override
    public void updateBySearch(String term) {
        Album album = new Album("Rise Against", BasicUtils.getResourceUrl(TestDataModel.class, "cover.png"), "rock", "The Black Market");
        album.addTrack("Track 1", 1, BasicUtils.getResourceUrl(TestDataModel.class, "jesters_cap-undo.the-chains.mp3"));
        album.addTrack("Track 2", 1, BasicUtils.getResourceUrl(TestDataModel.class, "jesters_cap-undo.the-chains.mp3"));
        album.addTrack("Track 3", 1, BasicUtils.getResourceUrl(TestDataModel.class, "jesters_cap-undo.the-chains.mp3"));
        album.addTrack("Track 4", 1, BasicUtils.getResourceUrl(TestDataModel.class, "jesters_cap-undo.the-chains.mp3"));
        getAlbums().add(album);
    }
}
