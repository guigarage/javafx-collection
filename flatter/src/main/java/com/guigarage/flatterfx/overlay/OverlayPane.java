package com.guigarage.flatterfx.overlay;

import java.util.UUID;

import javafx.scene.Node;
import javafx.scene.image.Image;

public interface OverlayPane {

	public UUID show(String title, Image icon, Node node, OverlayType type);
	
	public void hide(UUID showId);
}
