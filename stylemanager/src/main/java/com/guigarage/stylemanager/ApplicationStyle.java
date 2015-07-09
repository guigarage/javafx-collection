package com.guigarage.stylemanager;

public interface ApplicationStyle extends StyleProvider{

	String getUniqueName();
	
	StyleType getType();
	
	String getPreferredOsName();
}
