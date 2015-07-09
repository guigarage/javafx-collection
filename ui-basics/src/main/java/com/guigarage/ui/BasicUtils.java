package com.guigarage.ui;

import java.net.URL;

/**
 * Created by hendrikebbers on 27.08.14.
 */
public class BasicUtils {

    public static String getResourceUrl(Class loaderClass, String resourceName) {
        URL resourceUrl = loaderClass.getResource(resourceName);
        if (resourceUrl == null) {
            throw new NullPointerException("Can't find resource " + resourceName + " in package " + loaderClass.getPackage().toString());
        }
        return resourceUrl.toExternalForm();
    }
}
