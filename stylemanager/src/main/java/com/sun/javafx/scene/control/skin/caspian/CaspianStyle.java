package com.sun.javafx.scene.control.skin.caspian;

import com.guigarage.stylemanager.AbstractApplicationStyle;
import com.guigarage.stylemanager.StyleType;

public class CaspianStyle extends AbstractApplicationStyle {

	private final static String CASPIAN_CSS_FILE = "caspian.css";

	public CaspianStyle() {
		super(CaspianStyle.class.getResource(CASPIAN_CSS_FILE));
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
