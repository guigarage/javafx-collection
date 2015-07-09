package com.guigarage.imaging;

import java.awt.*;

public enum InterpolationType {
    NEAREST_NEIGHBOR(RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR),
    BICUBIC(RenderingHints.VALUE_INTERPOLATION_BICUBIC),
    BILINEAR((RenderingHints.VALUE_INTERPOLATION_BILINEAR));

    private Object awtType;

    private InterpolationType(Object awtType) {
        this.awtType = awtType;
    }

    public Object getAwtType() {
        return awtType;
    }
}
