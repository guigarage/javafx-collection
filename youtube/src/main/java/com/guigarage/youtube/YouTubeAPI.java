package com.guigarage.youtube;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

/**
 * Created by hendrikebbers on 10.10.14.
 */
public class YouTubeAPI {

    public static String getThumbnailUrl(String id) throws IOException {
        return getJsonObject(id, "thumbnail_url");
    }

    public static String getTitle(String id) throws IOException {
        return getJsonObject(id, "title");
    }

    public static double getPrefWidth(String id) throws IOException {
        return Double.parseDouble(getJsonObject(id, "width"));
    }

    public static double getPrefHeight(String id) throws IOException {
        return Double.parseDouble(getJsonObject(id, "height"));
    }

    private static String getJsonObject(String id, String name) throws IOException {
        URL infoUrl = new URL("http://www.youtube.com/oembed?url=http%3A//www.youtube.com/watch?v=" + id + "&format=json");
        JSONObject object = new JSONObject(IOUtils.toString(infoUrl.openConnection().getInputStream()));
        return object.get(name).toString();
    }
}


