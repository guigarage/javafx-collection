package com.guigarage.demos.itunes.views;

import com.guigarage.demos.itunes.api.FlowTest;
import com.guigarage.demos.itunes.api.FlowTestView;

import java.util.concurrent.TimeUnit;

public class SearchView extends FlowTestView {

    public SearchView(FlowTest handler) {
        super(handler);
    }

    public AlbumOverviewView search(String term) {
        getTestHandler().click("#searchField").type("Rise Against");
        getTestHandler().click("#goButton").sleep(1, TimeUnit.SECONDS);
        return new AlbumOverviewView(getTestHandler());
    }
}
