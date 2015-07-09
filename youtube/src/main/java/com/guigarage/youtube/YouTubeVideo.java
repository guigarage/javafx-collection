package com.guigarage.youtube;

import io.datafx.core.concurrent.DataFxCallable;
import io.datafx.core.concurrent.ObservableExecutor;
import io.datafx.core.concurrent.ProcessChain;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Worker;
import javafx.scene.image.Image;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.net.URL;
import java.util.concurrent.Callable;

public class YouTubeVideo {

    private final String id;

    public YouTubeVideo(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Worker<String> getTitle() {
        DataFxCallable<String> callable = () -> YouTubeAPI.getTitle(id);
        return ObservableExecutor.getDefaultInstance().submit((Callable) callable);
    }

    public Worker<Double> getPrefWidth() {
        DataFxCallable<Double> callable = () -> YouTubeAPI.getPrefWidth(id);
        return ObservableExecutor.getDefaultInstance().submit((Callable) callable);
    }

    public Worker<Double> getPrefHeight() {
        DataFxCallable<Double> callable = () -> YouTubeAPI.getPrefHeight(id);
        return ObservableExecutor.getDefaultInstance().submit((Callable) callable);
    }

    public Worker<Image> getThumbnail() {
        return ProcessChain.create().waitFor(getThumbnailUrl()).addFunctionInPlatformThread(url -> new Image(url)).run();
    }

    public Worker<String> getThumbnailUrl() {
        DataFxCallable<String> callable = () -> YouTubeAPI.getThumbnailUrl(id);
        return ObservableExecutor.getDefaultInstance().submit((Callable) callable);
    }

    public StringProperty createTitleProperty() {
        StringProperty titleProperty = new SimpleStringProperty();
        titleProperty.bind(getTitle().valueProperty());
        return titleProperty;
    }
}
