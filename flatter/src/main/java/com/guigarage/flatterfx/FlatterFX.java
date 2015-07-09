package com.guigarage.flatterfx;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.Scene;
import javafx.scene.control.PopupControl;

import com.guigarage.flatterfx.overlay.OverlayPane;
import com.sun.javafx.css.StyleManager;

public class FlatterFX {

    private final static String FLATTER_CSS_FILE = "flatterfx.css";
    
    private Map<WeakReference<Scene>, WeakReference<OverlayPane>> layers;
    
    private static FlatterFX instance;

    private FlatterInputType inputType = FlatterInputType.DEFAULT;
    
    private FlatterFX() {
    	layers = new HashMap<>();
    }

    public static synchronized FlatterFX getInstance() {
        if (instance == null) {
            instance = new FlatterFX();
        }
        return instance;
    }

    public FlatterInputType getInputType() {
		return inputType;
	}
    
    /**
     * Applies FlatterFx skinning to the application.
     */
    public static void style(FlatterInputType inputType) {    	
		StyleManager.getInstance().addUserAgentStylesheet(FlatterFX.class.getResource(FLATTER_CSS_FILE).toExternalForm());
    }
    
    /**
     * Applies FlatterFx skinning to the application.
     */
    public static void style() {
        style(FlatterInputType.DEFAULT);
    }
    
    public void setOverlayLayerForScene(Scene scene, OverlayPane overlayLayer) {
    	layers.put(new WeakReference<>(scene), new WeakReference<>(overlayLayer));
    }
    
    public OverlayPane getOverlayLayer(Scene scene) {
    	for(WeakReference<Scene> keyRef : layers.keySet()) {
    		Scene keyScene = keyRef.get();
    		if(keyScene != null && keyScene.equals(scene)) {
    			return layers.get(keyRef).get();
    		}
    	}
    	
    	PopupControl popupControl = new PopupControl();
    	//TODO: Auf Basis des popup ein OverlayPane bauen
    	
    	return null;
    }
}
