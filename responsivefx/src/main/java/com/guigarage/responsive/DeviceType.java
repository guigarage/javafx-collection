package com.guigarage.responsive;

import javafx.css.PseudoClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by hendrikebbers on 01.09.14.
 */
public enum DeviceType {
    EXTRA_SMALL, SMALL, MEDIUM, LARGE, NONE;

    public static final String STYLESHEET_XS = "skin-xs.css";

    public static final String STYLESHEET_SM = "skin-sm.css";

    public static final String STYLESHEET_MD = "skin-md.css";

    public static final String STYLESHEET_LG = "skin-lg.css";

    public static final double MAX_SIZE_XS = 400;

    public static final double MAX_SIZE_SM = 768;

    public static final double MAX_SIZE_MD = 979;

    public static final PseudoClass PSEUDO_CLASS_XS = PseudoClass.getPseudoClass("extreme-small-device");

    public static final PseudoClass PSEUDO_CLASS_SM = PseudoClass.getPseudoClass("small-device");

    public static final PseudoClass PSEUDO_CLASS_MD = PseudoClass.getPseudoClass("medium-device");

    public static final PseudoClass PSEUDO_CLASS_LG = PseudoClass.getPseudoClass("large-device");

    public static final String STYLE_CLASS_XS_VISIBLE = "visible-xs";

    public static final String STYLE_CLASS_SM_VISIBLE = "visible-sm";

    public static final String STYLE_CLASS_MD_VISIBLE = "visible-md";

    public static final String STYLE_CLASS_LG_VISIBLE = "visible-lg";

    public static DeviceType getForWidth(double width) {
        if(width < MAX_SIZE_XS) {
            return EXTRA_SMALL;
        } else if(width < MAX_SIZE_SM) {
            return SMALL;
        } else if(width < MAX_SIZE_MD) {
            return MEDIUM;
        } else {
            return LARGE;
        }
    }

    public static List<PseudoClass> getAllClasses() {
        return new ArrayList<>(Arrays.asList(PSEUDO_CLASS_XS, PSEUDO_CLASS_SM, PSEUDO_CLASS_MD, PSEUDO_CLASS_LG));
    }

    public List<PseudoClass> getInactiveClasses() {
        List<PseudoClass> classes = getAllClasses();
        classes.removeAll(getActiveClasses());
        return classes;
    }

    public List<PseudoClass> getActiveClasses() {
        if(this.equals(EXTRA_SMALL)) {
            return Collections.singletonList(PSEUDO_CLASS_XS);
        } else if(this.equals(SMALL)) {
            return Collections.singletonList(PSEUDO_CLASS_SM);
        } else if(this.equals(MEDIUM)) {
            return Collections.singletonList(PSEUDO_CLASS_MD);
        } else {
            return Collections.singletonList(PSEUDO_CLASS_LG);
        }
    }

    public String getStylesheet() {
        if(this.equals(EXTRA_SMALL)) {
            return DeviceType.class.getResource(STYLESHEET_XS).toExternalForm();
        } else if(this.equals(SMALL)) {
            return DeviceType.class.getResource(STYLESHEET_SM).toExternalForm();
        } else if(this.equals(MEDIUM)) {
            return DeviceType.class.getResource(STYLESHEET_MD).toExternalForm();
        } else {
            return DeviceType.class.getResource(STYLESHEET_LG).toExternalForm();
        }
    }
}
