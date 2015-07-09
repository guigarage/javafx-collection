package com.guigarage.incubator.imageviewer.data;

import com.guigarage.imaging.ImageUtilities;
import com.sun.javafx.application.PlatformImpl;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.image.Image;

import java.util.concurrent.*;

public class BetterImageObject {

    private static ExecutorService executorService = Executors.newFixedThreadPool(2);
    private String imageUrl;
    private ReadOnlyObjectWrapper<Image> cachedImage;
    private Future<Void> resizeWorker;

    public BetterImageObject(String url) {
        this.imageUrl = url;
        cachedImage = new ReadOnlyObjectWrapper<>();
    }

    public synchronized void loadImage(int width, int height) {
        boolean restart = true;
        if (resizeWorker != null) {
            if (resizeWorker.isDone()) {
                restart = false;
            } else {
                resizeWorker.cancel(true);
            }
        }
        if (cachedImage.get() != null && cachedImage.get().getWidth() >= width && cachedImage.get().getHeight() >= height) {
            if (cachedImage.get().getWidth() != width || cachedImage.get().getHeight() != height) {
                cachedImage.set(ImageUtilities.fastResize(cachedImage.get(), width, height));
                resizeWorker = createImageResizeWorker(width, height);
            }
            if (restart) {
                resizeWorker = createImageResizeWorker(width, height);
            }
        } else {
            resizeWorker = createImageResizeWorker(width, height);
        }

    }

    private Future<Void> createImageResizeWorker(int width, int height) {
        Callable<Void> task = () -> {
            if (width <= 0 || height <= 0) {
                return null;
            }
            final Image resizedImage = ImageUtilities.fastResize(getOriginalImage(), width, height);
//            final Image resizedImage = ImageUtilities.progressiveResize(getOriginalImage(), width, height, InterpolationType.BILINEAR);
            PlatformImpl.runAndWait(() -> {
                cachedImage.set(resizedImage);
            });
            return null;
        };
        return executorService.submit(task);
    }

    public Image getCachedImage() {
        return cachedImage.get();
    }

    public ReadOnlyObjectProperty<Image> cachedImageProperty() {
        return cachedImage.getReadOnlyProperty();
    }

    public Image getOriginalImage() {
        try {
            return Executors.newSingleThreadExecutor().submit((Callable<Image>) () -> {
                Image i = new Image(imageUrl, false);
                return i;
            }).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
