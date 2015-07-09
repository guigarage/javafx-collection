package com.guigarage.stylemanager;

public abstract class AbstractApplicationStylePlugin implements ApplicationStylePlugin {

	private ApplicationStyle styles;
	
	public AbstractApplicationStylePlugin(ApplicationStyle styles) {
		this.styles = styles;
	}

	@Override
	public String getUniqueStyleName() {
		return styles.getUniqueName();
	}
}
