package com.guigarage.stylemanager;

public interface ApplicationStylePlugin extends StyleProvider {

	String getUniqueStyleName();
	
	int getWeight();
}
