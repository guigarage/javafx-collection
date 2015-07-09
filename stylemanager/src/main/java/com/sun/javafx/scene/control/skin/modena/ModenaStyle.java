package com.sun.javafx.scene.control.skin.modena;

import com.guigarage.stylemanager.AbstractApplicationStyle;
import com.guigarage.stylemanager.StyleType;

public class ModenaStyle extends AbstractApplicationStyle {

	private final static String MODENA_CSS_FILE = "modena.css";

	public ModenaStyle() {
		super(ModenaStyle.class.getResource(MODENA_CSS_FILE));
	}
	
	@Override
	public StyleType getType() {
		return StyleType.CROSS_PLATFORM;
	}

	@Override
	public String getPreferredOsName() {
		return null;
	}

}
