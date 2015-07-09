package com.guigarage.flatterfx.controls;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.image.Image;

import com.guigarage.flatterfx.skin.FlatterAvatarViewSkin;

public class AvatarView extends Control {

	private SimpleObjectProperty<Image> image;
	
	private SimpleDoubleProperty defaultSize;
	
	private SimpleDoubleProperty gap;
	
	public AvatarView() {
	}
	
	@Override
	protected Skin<?> createDefaultSkin() {
		return new FlatterAvatarViewSkin(this);
	}
	
	public SimpleDoubleProperty gapProperty() {
		if(gap == null) {
			gap = new SimpleDoubleProperty(12.0);
		}
		return gap;
	}
	
	public SimpleDoubleProperty defaultSizeProperty() {
		if(defaultSize == null) {
			defaultSize = new SimpleDoubleProperty(64.0);
		}
		return defaultSize;
	}
	
	public SimpleObjectProperty<Image> imageProperty() {
		if(image == null) {
			image = new SimpleObjectProperty<>();
		}
		return image;
	}
}
