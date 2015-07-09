package com.guigarage.imaging;

import com.guigarage.ui.UiUtilities;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Dimension2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

public class ImageUtilities {


    //META-INFO: http://www.javaxt.com/javaxt-core/javaxt.io.Image/

    public static Image fastResize(Image image, Dimension2D dimension) {
        return fastResize(image, (int) dimension.getWidth(), (int) dimension.getHeight());
    }

    public static Image fastResize(Image image, int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Dimension must be > 0 (" + width + "/" + height + ")");
        }
        PixelReader reader = image.getPixelReader();
        WritableImage writableImage = new WritableImage(width, height);
        PixelWriter writer = writableImage.getPixelWriter();

        double widthFactor = image.getWidth() / width;
        double heightFactor = image.getHeight() / height;

        int xToWrite = 0;
        while (xToWrite < width) {
            int yToWrite = 0;
            while (yToWrite < height) {
                writer.setArgb(xToWrite, yToWrite, reader.getArgb((int) (xToWrite * widthFactor), (int) (yToWrite * heightFactor)));
                yToWrite++;
            }
            xToWrite++;
        }
        return writableImage;
    }

    public static Image resizeByAwt(Image image, int width, int height) {
        return resizeByAwt(image, width, height, InterpolationType.NEAREST_NEIGHBOR);
    }

    public static Image resizeByAwt(Image image, int width, int height, InterpolationType type) {
        BufferedImage awtImage = SwingFXUtils.fromFXImage(image, null);
        BufferedImage resizedAwtImage = AwtImageUtilities.createCompatibleImage(width, height, true);
        Graphics2D g = resizedAwtImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, type.getAwtType());
        g.drawImage(awtImage, 0, 0, width, height, null);
        g.dispose();
        return SwingFXUtils.toFXImage(resizedAwtImage, null);
    }

    public static Image progressiveResize(Image image, int width, int height, InterpolationType type) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Dimension must be > 0 (" + width + "/" + height + ")");
        }
        if (image.getWidth() == width && image.getHeight() == height) {
            return new WritableImage(image.getPixelReader(), width, height);
        }
        if (image.getWidth() * 2 < width && image.getHeight() * 2 < height) {
            Image resizedImage = fastResize(image, (int) image.getWidth() * 2, (int) image.getHeight() * 2);
            return progressiveResize(resizedImage, width, height);
        }
        if (image.getWidth() / 2 > width && image.getHeight() / 2 > height) {
            Image resizedImage = fastResize(image, (int) image.getWidth() / 2, (int) image.getHeight() / 2);
            return progressiveResize(resizedImage, width, height);
        }
        return resizeByAwt(image, width, height, type);
    }

    public static Image progressiveResize(Image image, int width, int height) {
        return progressiveResize(image, width, height, InterpolationType.NEAREST_NEIGHBOR);
    }

    public static Image filter(Image image, BufferedImageOp filter) {
        BufferedImage awtImage = SwingFXUtils.fromFXImage(image, null);
        BufferedImage filteredAwtImage = AwtImageUtilities.createCompatibleImage(awtImage.getWidth(), awtImage.getHeight(), true);
        filteredAwtImage = filter.filter(awtImage, filteredAwtImage);
        return SwingFXUtils.toFXImage(filteredAwtImage, null);
    }

    public static Image filter(Image image, ImageFilter filter) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        WritableImage filteredImage = new WritableImage(width, height);
        int[] inPixels = getRGB(image);
        int[] outPixels = filter.filter(width, height, inPixels);

        setRGB(filteredImage, 0, 0, width, height, outPixels);
        return filteredImage;
    }

    public static void setRGB(WritableImage image, int[] rgb) {
        setRGB(image, 0, 0, (int) image.getWidth(), (int) image.getHeight(), rgb);
    }

    public static int[] getRGB(Image image) {
        return getRGB(image, 0, 0, (int) image.getWidth(), (int) image.getHeight());
    }

    public static void setRGB(WritableImage image, int x, int y, int width, int height, int[] rgb) {
        PixelWriter writer = image.getPixelWriter();
        writer.setPixels(x, y, width, height, PixelFormat.getIntArgbInstance(), rgb, 0, width);
    }

    public static int[] getRGB(Image image, int x, int y, int width, int height) {
        int[] rgb = new int[width * height];
        PixelReader reader = image.getPixelReader();
        reader.getPixels(x, y, width, height, PixelFormat.getIntArgbInstance(), rgb, 0, width);
        return rgb;
    }

    public static double getMatchingHeight(double width, Image i) {
        return ((i.getHeight() * width) / i.getWidth());
    }

    public static double getMatchingWidth(double height, Image i) {
        return ((i.getWidth() * height) / i.getHeight());
    }

    public static Image clipImage(Image image, Rectangle2D clip) {
        WritableImage clippedImage = new WritableImage((int) clip.getWidth(), (int) clip.getHeight());
        int[] rgbaValues = getRGB(image, (int) clip.getMinX(), (int) clip.getMinY(), (int) clip.getWidth(), (int) clip.getHeight());
        setRGB(clippedImage, rgbaValues);
        return clippedImage;
    }

    public static Image fitInDimensionAndCutEdges(Image image, Dimension2D dimension) {
        return fitInDimensionAndCutEdges(image, dimension.getWidth(), dimension.getHeight());
    }

    public static Image fitInDimensionAndCutEdges(Image image, double width, double heigth) {
        Dimension2D newImageDimension = UiUtilities.shouldFitIn(image.getWidth(), image.getHeight(), width, heigth);
        Image resizedImage = fastResize(image, newImageDimension);
        return clipImage(resizedImage, new Rectangle2D((resizedImage.getWidth() - width) / 2, (resizedImage.getHeight() - heigth) / 2, width, heigth));
    }
}
