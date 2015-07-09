package com.guigarage.ui.property;

import javafx.beans.property.StringProperty;

import java.util.function.Supplier;

/**
 * Created by hendrikebbers on 11.09.14.
 */
public class PropertySuppliers {

    public static Supplier<String> create(StringProperty property) {
        return () -> property.get();
    }
}
