package com.guigarage.stylemanager;

public class OsInformationProvider {

	private static OsInformationProvider instance;
	
	private String osName;
	
	private OsInformationProvider() {
		osName = System.getProperty("os.name").toLowerCase();
	}
	
	public static OsInformationProvider getInstance() {
		return instance;
	}
	
	public boolean isWindows() {
		return (osName.indexOf("win") >= 0);
	}
 
	public boolean isMac() {
		return (osName.indexOf("mac") >= 0);
	}
 
	public boolean isLinux() {
		return (osName.indexOf("nux") >= 0);
	}

	public boolean isCurrentOs(String name) {
		if(name != null) {
			if(name.toLowerCase().startsWith("win") && isWindows()) {
				return true;
			}
			if(name.toLowerCase().startsWith("linux") && isLinux()) {
				return true;
			}
			if(name.toLowerCase().startsWith("mac") && isMac()) {
				return true;
			}
		}
		return false;
	}
}
