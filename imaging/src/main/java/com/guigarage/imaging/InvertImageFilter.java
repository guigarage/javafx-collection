package com.guigarage.imaging;

import javafx.scene.paint.Color;

public class InvertImageFilter implements ImageFilter {

    @Override
    public int[] filter(int imageWidth, int imageHeigth, int[] inPixels) {
        int[] filteredPixels = new int[inPixels.length];

        for(int index = 0; index < inPixels.length; index++) {
            int argb = inPixels[index];
            int a = (argb >> 24) & 0xff;
            int r = (argb >> 16) & 0xff;
            int g = (argb >> 8) & 0xff;
            int b = argb & 0xff;
            Color c = Color.rgb(r, g, b, a / 255.0);
            c = c.invert();
            filteredPixels[index] = ((int)(c.getOpacity() *  255.0) << 24) | ((int)(c.getRed() *  255.0) << 16) | ((int)(c.getGreen() *  255.0) << 8) | (int)(c.getBlue() *  255.0);
        }
        return filteredPixels;
    }
}
