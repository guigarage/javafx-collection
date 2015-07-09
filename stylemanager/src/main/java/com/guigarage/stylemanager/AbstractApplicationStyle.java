package com.guigarage.stylemanager;

import java.net.URL;

public abstract class AbstractApplicationStyle implements ApplicationStyle {

	private URL[] cssUrls;
	
	public AbstractApplicationStyle(URL... cssUrls) {
		this.cssUrls = cssUrls;
	}
	
	@Override
	public URL[] getCssUrls() {
		return cssUrls;
	}

	@Override
	public String getUniqueName() {
		return this.getClass().getName();
	}
}
