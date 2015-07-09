package com.guigarage.imaging;

import javafx.scene.image.Image;

public interface ImageFilter {

    int[] filter(int imageWidth, int imageHeigth, int[] inPixels);

}
