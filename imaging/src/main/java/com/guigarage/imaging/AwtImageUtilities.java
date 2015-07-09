package com.guigarage.imaging;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AwtImageUtilities {

    public static BufferedImage createCompatibleImage(int width, int height, boolean alpha) {
        // Create a buffered image with a format that's compatible with the screen
        BufferedImage bimage = null;

        String bool = System.getProperty("java.awt.headless");

        if (!GraphicsEnvironment.isHeadless() && !Boolean.parseBoolean(bool)) {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            try {
                // Determine the type of transparency of the new buffered image
                int transparency = Transparency.OPAQUE;
                if (alpha) {
                    transparency = Transparency.TRANSLUCENT;
                }

                // Create the buffered image
                GraphicsDevice gs = ge.getDefaultScreenDevice();
                GraphicsConfiguration gc = gs.getDefaultConfiguration();
                bimage = gc.createCompatibleImage(width, height, transparency);
            } catch (HeadlessException e) {
                // The system does not have a screen
            }
        }
        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            if (alpha) {
                type = BufferedImage.TYPE_INT_ARGB;
            }
            bimage = new BufferedImage(width, height, type);
        }
        return bimage;
    }

    public static BufferedImage createCompatibleImage(BufferedImage image) {
        BufferedImage ret = createCompatibleImage(image.getWidth(), image.getHeight(), true);
        Graphics g = ret.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return ret;
    }

    /**
     * Progressive bilinear scaling: for any downscale size, scale iteratively by halves using BILINEAR filtering until
     * the proper size is reached.
     */
    public static BufferedImage getOptimalScalingImage(BufferedImage inputImage, int width, int height) {
        int currentWidth = inputImage.getWidth();
        int currentHeigth = inputImage.getHeight();
        BufferedImage currentImage = inputImage;
        int deltaX = currentWidth - width;
        int deltaY = currentHeigth - height;
        int nextPow2X = currentWidth >> 1;
        int nextPow2Y = currentHeigth >> 1;
        while (currentWidth > 1 && currentHeigth > 1) {
            if (deltaX <= nextPow2X || deltaY <= nextPow2Y) {
                if (currentWidth != width || currentHeigth != height) {
                    BufferedImage tmpImage = createCompatibleImage(width, height, true);
                    Graphics g = tmpImage.getGraphics();
                    ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                            RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                    g.drawImage(currentImage, 0, 0, tmpImage.getWidth(), tmpImage.getHeight(), null);
                    currentImage = tmpImage;
                }
                return currentImage;
            } else {
                BufferedImage tmpImage = createCompatibleImage(currentWidth >> 1, currentHeigth >> 1, true);
                Graphics g = tmpImage.getGraphics();
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                g.drawImage(currentImage, 0, 0, tmpImage.getWidth(), tmpImage.getHeight(), null);
                currentImage = tmpImage;
                currentWidth = currentImage.getWidth();
                currentHeigth = currentImage.getHeight();
                deltaX = currentWidth - width;
                deltaY = currentHeigth - height;
                nextPow2X = currentWidth >> 1;
                nextPow2Y = currentHeigth >> 1;
            }
        }
        return currentImage;
    }

    public static BufferedImage resizeToNewWidth(int width, BufferedImage i) {
        int newHeigth = (i.getHeight() * width) / i.getWidth();
        return getOptimalScalingImage(i, width, newHeigth);
    }

    public static BufferedImage resizeToNewHeigth(int heigth, BufferedImage i) {
        int newWidth = (i.getWidth() * heigth) / i.getHeight();
        return getOptimalScalingImage(i, newWidth, heigth);
    }
}
