package com.guigarage.incubator.imageviewer.data;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDescriptor;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.guigarage.imaging.ImageUtilities;
import com.guigarage.imaging.InterpolationType;
import com.guigarage.ui.UiUtilities;
import com.sun.javafx.application.PlatformImpl;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.Dimension2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

import java.io.File;
import java.net.URI;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ImageObject {

    private static ExecutorService executorService = Executors.newFixedThreadPool(2);
    private Image originalImage;
    private ReadOnlyObjectWrapper<Image> cachedImage;
    private Future<Void> resizeWorker;

    public ImageObject(String url) {
        this(new Image(url));
    //    try {
    //        Metadata metadata = ImageMetadataReader.readMetadata(new File(new URI(url)));
    //        ExifSubIFDDirectory directory = metadata.getDirectory(ExifSubIFDDirectory.class);
    //        ExifSubIFDDescriptor descriptor = new ExifSubIFDDescriptor(directory);
    //        System.out.println(descriptor.getShutterSpeedDescription());
    //    } catch (Exception e) {
    //        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    //    }
    }

    public ImageObject(Image image) {
        this.originalImage = image;
        cachedImage = new ReadOnlyObjectWrapper<>();
    }

    public Image getForSize(int width, int height) {
        boolean restart = true;
        if (resizeWorker != null && resizeWorker.isDone()) {
            restart = false;
        }
        stopWorker();
        if (originalImage.getWidth() == width && originalImage.getHeight() == height) {
            return originalImage;
        }
        if (cachedImage.get() != null && cachedImage.get().getWidth() == width && cachedImage.get().getHeight() == height) {
            if (restart) {
                resizeWorker = createImageResizeWorker(width, height);
            }
            return cachedImage.get();
        }
        cachedImage.set(ImageUtilities.fitInDimensionAndCutEdges(originalImage, width, height));

        //resizeWorker = createImageResizeWorker((int)dim.getWidth(), (int)dim.getHeight());
        return cachedImage.get();
    }

    public void stopWorker() {
        if (resizeWorker != null) {
            resizeWorker.cancel(true);
        }
    }

    private Future<Void> createImageResizeWorker(int width, int height) {
        Callable<Void> task = () -> {
            if (width <= 0 || height <= 0) {
                return null;
            }
            Dimension2D dim = UiUtilities.shouldFitIn(new Dimension2D(originalImage.getWidth(), originalImage.getHeight()), new Dimension2D(width, height));
            final Image resizedImage = ImageUtilities.progressiveResize(originalImage, (int)dim.getWidth(), (int)dim.getHeight(), InterpolationType.BILINEAR);
            PlatformImpl.runAndWait(() -> {
                cachedImage.set(ImageUtilities.clipImage(resizedImage, new Rectangle2D((resizedImage.getWidth() - width) / 2, (resizedImage.getHeight() - height) / 2, width, height)));
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
        return originalImage;
    }
}
