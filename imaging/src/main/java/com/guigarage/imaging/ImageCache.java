package com.guigarage.imaging;

import javafx.concurrent.Task;
import javafx.scene.image.Image;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class ImageCache {

    private Executor executor;

    private long pixelCount = 0;

    private long maxPixelCount = 2000 * 2000 * 5;

    private Map<ImageIdentifier, WeakReference<Image>> cache;

    private List<ImageIdentifier> usageData;

    public ImageCache(Executor executor) {
        this.executor = executor;
        usageData = new CopyOnWriteArrayList<>();
        cache = new ConcurrentHashMap<>();
    }

    private synchronized void addToCache(ImageIdentifier identifier, Image image) {
        cleanCache((int) image.getHeight() * (int) image.getWidth());
        cache.put(identifier, new WeakReference<Image>(image));
        updateUsage(identifier);
        pixelCount = pixelCount + (int) image.getHeight() * (int) image.getWidth();
    }

    private synchronized void updateUsage(ImageIdentifier imageIdentifier) {
        if(!cache.containsKey(imageIdentifier)) {
            throw new RuntimeException("TODO");
        }
        usageData.remove(imageIdentifier);
        usageData.add(imageIdentifier);
    }

    private void cleanCache(long neededPixels) {
        for(Map.Entry<ImageIdentifier,WeakReference<Image>> entry : cache.entrySet()) {
            if(entry.getValue().get() == null) {
                ImageIdentifier identifier = entry.getKey();
                remove(identifier);
            }
        }
        while(maxPixelCount < pixelCount + neededPixels) {
            removeEldest();
        }
    }

    private void removeEldest() {
        ImageIdentifier identifier = usageData.get(0);
        remove(identifier);
    }

    private synchronized void remove(ImageIdentifier identifier) {
        cache.remove(identifier);
        usageData.remove(identifier);
        pixelCount = pixelCount - identifier.getHeight() * identifier.getWidth();
    }

    private Image getCachedImage(ImageIdentifier imageIdentifier) {
        WeakReference<Image> ref = cache.get(imageIdentifier);
        if(ref != null) {
            return ref.get();
        }
        return null;
    }

    public Future<Image> fetchImage(ImageIdentifier identifier) {
        Task<Image> task = new Task<Image>() {
            @Override
            protected Image call() throws Exception {
                updateTitle("Image Cache Loader");

                //Check if matching Image is in cache
                Image image = getCachedImage(identifier);
                if(image != null) {
                    if(image.getWidth() == identifier.getWidth() && image.getHeight() == identifier.getHeight()) {
                        updateUsage(identifier);
                        return image;
                    }
                }

                //Create new Image
                image = new Image(identifier.getUrl(), false);
                image = ImageUtilities.fitInDimensionAndCutEdges(image, identifier.getWidth(), identifier.getHeight());
                addToCache(identifier, image);

                return image;
            }
        };
        executor.execute(task);
        return task;
    }

}
