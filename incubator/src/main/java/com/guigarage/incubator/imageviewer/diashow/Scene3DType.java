package com.guigarage.incubator.imageviewer.diashow;

import java.util.Random;

/**
 * Created by hendrikebbers on 07.07.14.
 */
public enum Scene3DType {
    A, B, C, D, E;

    public static Scene3DType random() {
        int i = new Random(System.currentTimeMillis()).nextInt(Scene3DType.values().length);
        return Scene3DType.values()[i];
    }
}
