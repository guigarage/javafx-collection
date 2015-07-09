package com.guigarage.stylemanager;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import javafx.application.Application;

import com.sun.javafx.css.StyleManager;

public class ApplicationStyleManager {

	private static ApplicationStyleManager instance;

	private ApplicationStyleManager() {
	}

	public static ApplicationStyleManager getInstance() {
		if (instance == null) {
			instance = new ApplicationStyleManager();
		}
		return instance;
	}

	public ApplicationStyle findSystemStyle() {
		for(ApplicationStyle stylesheet : loadAllStylesheet()) {
			StyleType type = stylesheet.getType();
			String osName = stylesheet.getPreferredOsName();
			if(osName != null && type != null && type.equals(StyleType.SYSTEM)) {
				if(OsInformationProvider.getInstance().isCurrentOs(osName)) {
					return stylesheet;
				}
			}
		}
		return null;
	}
	
	public ApplicationStyle findStyle(String uniqueName) {
		for(ApplicationStyle stylesheet : loadAllStylesheet()) {
			if(uniqueName.equals(stylesheet.getUniqueName())) {
				return stylesheet;
			}
		}
		return null;
	}
	
	public void styleApplication(Class<ApplicationStyle> styleClass) throws InstantiationException, IllegalAccessException {
		styleApplication(styleClass.newInstance());
	}
	
	public void styleApplication(String styleName) {
		styleApplication(findStyle(styleName));
	}
	
	public void styleApplication(ApplicationStyle stylesheet) {
		Application.setUserAgentStylesheet(null);
		boolean mainStylesheet = true;
		for (URL cssUrl : stylesheet.getCssUrls()) {
			String fName = cssUrl.toExternalForm();
			if(mainStylesheet) {
				Application.setUserAgentStylesheet(fName);
				mainStylesheet = false;
			} else {
				StyleManager.getInstance().addUserAgentStylesheet(fName);
			}
		}
		for(ApplicationStylePlugin plugin : loadPluginsForStylesheet(stylesheet)) {
			for (URL cssUrl : plugin.getCssUrls()) {
				String fName = cssUrl.toExternalForm();
				StyleManager.getInstance().addUserAgentStylesheet(fName);
			}
		}
	}
	
	private List<ApplicationStyle> loadAllStylesheet() {
		ServiceLoader<ApplicationStyle> loader = ServiceLoader.load(ApplicationStyle.class);
		Iterator<ApplicationStyle> iterator = loader.iterator();
		
		List<ApplicationStyle> ret = new ArrayList<>();
		while(iterator.hasNext()) {
			ret.add(iterator.next());
		}
		return ret;
	}
	
	private List<ApplicationStylePlugin> loadPluginsForStylesheet(ApplicationStyle stylesheet) {
		String stylesheetName = stylesheet.getUniqueName();
		
		ServiceLoader<ApplicationStylePlugin> loader = ServiceLoader.load(ApplicationStylePlugin.class);
		Iterator<ApplicationStylePlugin> iterator = loader.iterator();
		
		List<ApplicationStylePlugin> ret = new ArrayList<>();
		while(iterator.hasNext()) {
			ApplicationStylePlugin plugin = iterator.next();
			String pluginName = plugin.getUniqueStyleName();
			if(pluginName != null && pluginName.equals(stylesheetName)) {
				ret.add(plugin);
			}
		}
		return ret;
	}
}
