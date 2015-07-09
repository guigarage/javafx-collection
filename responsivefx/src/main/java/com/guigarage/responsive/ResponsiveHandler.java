package com.guigarage.responsive;

import com.guigarage.ui.WindowUtilities;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.stage.Window;

import javafx.beans.property.BooleanProperty;


public class ResponsiveHandler {

    private static String PROP_MANAGED_STATE = "responsivefx-preset-managed-state";
    
    private static InvalidationListener MANAGED_LISTENER = e -> {
       BooleanProperty managed = (BooleanProperty) e;
       Node node = (Node) managed.getBean();
       node.getProperties().put(PROP_MANAGED_STATE, node.isManaged());
   };

    public static void addResponsiveToWindow(Window window) {
        StringProperty stylesheet = new SimpleStringProperty(getCurrentResponsiveStylesheet(window));
        WindowUtilities.bindStyleSheetToWindow(window, stylesheet);

        updatePseudoClassesForAllChildren(window);
        //TODO: Falsch! Hier muss der ursprünglich gesetzte Wert gespeichert werden! managed müsste eine styledProperty sein
        updateManagedPropertyForAllChildren(window);

        WindowUtilities.registerRecursiveChildObserver(window, n -> removeAllPseudoClasses(n), n -> updatePseudoClasses(n, getTypeForWindow(window)));

        window.widthProperty().addListener(e -> {
            stylesheet.setValue(getCurrentResponsiveStylesheet(window));
            updatePseudoClassesForAllChildren(window);
            updateManagedPropertyForAllChildren(window);
        });
        window.getScene().getRoot().layout();
    }

    private static DeviceType getTypeForWindow(Window window) {
        return DeviceType.getForWidth(window.getWidth());
    }

    private static String getCurrentResponsiveStylesheet(Window window) {
        return getTypeForWindow(window).getStylesheet();
    }


    private static void updateManagedPropertyForAllChildren(Window window) {
        WindowUtilities.getAllNodesInWindow(window).forEach(n -> {
            updateManagedProperty(n, getTypeForWindow(window));
        });
    }

    private static void updateManagedProperty(Node n, DeviceType type) {
        // first time we've set this invisible => store the preset
      if ( !n.getProperties().containsKey(PROP_MANAGED_STATE)) {
          n.getProperties().put(PROP_MANAGED_STATE, n.isManaged()) ;         
      }
      // don't track changes through this
       n.managedProperty().removeListener(MANAGED_LISTENER);
       // If it's visible we use the stored value for "managed" property
       n.setManaged(n.isVisible()? (Boolean)n.getProperties().get(PROP_MANAGED_STATE):false);
       // need to track changes through API
       n.managedProperty().addListener(MANAGED_LISTENER);
    }

    private static void updatePseudoClassesForAllChildren(Window window) {
        WindowUtilities.getAllNodesInWindow(window).forEach(n -> {
            updatePseudoClasses(n, getTypeForWindow(window));
        });
    }

    private static void updatePseudoClasses(Node n, DeviceType type) {
        type.getInactiveClasses().forEach(pseudoClass -> n.pseudoClassStateChanged(pseudoClass, false));
        type.getActiveClasses().forEach(pseudoClass -> n.pseudoClassStateChanged(pseudoClass, true));
        deviceTypeProperty.set(type);
    }

    private static void removeAllPseudoClasses(Node n) {
        DeviceType.getAllClasses().forEach(pseudoClass -> n.pseudoClassStateChanged(pseudoClass, false));
    }


    // ******************** Event handling *************************************
    public static final ObjectProperty<DeviceType> deviceTypeProperty = new ObjectPropertyBase<DeviceType>(DeviceType.NONE) {
        @Override public Object getBean() { return this; }
        @Override public String getName() { return "deviceType"; }
    };
    public static final void setOnDeviceTypeChanged(final InvalidationListener LISTENER) {
        deviceTypeProperty.addListener(LISTENER);
    }
    public static final void setOnDeviceTypeChanged(final ChangeListener<DeviceType> LISTENER) {
        deviceTypeProperty.addListener(LISTENER);
    }
}
